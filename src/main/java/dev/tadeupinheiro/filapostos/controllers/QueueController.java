package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.QueueRecordDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import dev.tadeupinheiro.filapostos.services.NormalQueuePatientService;
import dev.tadeupinheiro.filapostos.services.NormalQueueService;
import dev.tadeupinheiro.filapostos.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/schedule-appointment")
public class QueueController {

    @Autowired
    private NormalQueuePatientService normalQueuePatientService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private NormalQueueService normalQueueService;

    @PostMapping
    public ResponseEntity<String> registerVacancy (@RequestBody @Valid QueueRecordDTO queueRecordDTO) {

        var normalQueue = normalQueueService.findNormalQueueById(queueRecordDTO.idQueue());
        var patient = patientService.findBySusNumber(queueRecordDTO.patientSusNumber());

        if (normalQueue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar essa fila");
        }
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar esse paciente");
        }

        //Cria uma lista com todos os pacientes que estão na fila passada como argumento no json
        List<NormalQueuePatient> normalQueuePatientList = normalQueuePatientService.findAllNormalQueuePatient()
                .stream()
                .filter(normalQueuePatient -> normalQueuePatient.getId().getNormalQueue().getId().equals(normalQueue.getId()))
                .toList();

        //Se a lista está vazia, quer dizer que não há pacientes na fila, portanto, cadastro como posição 1.
        if (normalQueuePatientList.isEmpty()){
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), 1));
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: " + "1");
        }

        //Se chegou aqui, a lista não está vazia.
        //Confere se paciente já está na fila
        if (normalQueuePatientList.stream().anyMatch(p -> p.getId().getPatient().getSusNumber().equalsIgnoreCase(queueRecordDTO.patientSusNumber()))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Paciente já está na fila");
        }

        //Aqui o paciente não tem prioridade
        if (patient.getPriorityType().equalsIgnoreCase("NORMAL")){
            int sizeList = normalQueuePatientList.size();
            ++sizeList;
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), sizeList));
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: " + sizeList);
        }

        //Aqui o paciente tem prioridade
        int positionLastPriority = 0;
        int sizeList = normalQueuePatientList.size();
        for (int i = 1; i <= sizeList; i++){
            var normalQueuePatient = normalQueuePatientList.get(i);
            if (!normalQueuePatient.getId().getPatient().getPriorityType().equalsIgnoreCase("NORMAL")){
                positionLastPriority = i; //Define qual a posição da última prioridade
            }
        }
        if (positionLastPriority == sizeList){
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), positionLastPriority));
        }
        if (positionLastPriority+1 == sizeList){
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), positionLastPriority+2));
        }
        if (positionLastPriority+1 < sizeList){
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), positionLastPriority+2));
            normalQueuePatientService.updateQueueBecausePriority(normalQueuePatientList, positionLastPriority+2);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Paciente entrou na fila e sua posição é: " + positionLastPriority+2);
    }

}
