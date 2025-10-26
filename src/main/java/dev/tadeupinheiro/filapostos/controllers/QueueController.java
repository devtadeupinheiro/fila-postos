package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.QueueRecordDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
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
import java.util.Objects;
import java.util.Optional;

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

        var normalQueue = normalQueueService.findNormalQueueWithVacanciesById(queueRecordDTO.idQueue());
        var patient = patientService.findBySusNumber(queueRecordDTO.patientSusNumber());

        if (normalQueue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar essa fila");
        }
        //Consulta se a lista tem vagas
        if (normalQueue.getQuantityVacancies() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Não tem mais vaga nessa fila");
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
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: 1");
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
        for (NormalQueuePatient normalQueuePatient : normalQueuePatientList){
            if (!normalQueuePatient.getId().getPatient().getPriorityType().equalsIgnoreCase("NORMAL")){
                positionLastPriority = normalQueuePatient.getPosition(); //Define qual a posição da última prioridade
            }
        }

        //Não tem na lista nenhum prioridade ou a prioridade não é o último da lista
        if (positionLastPriority < sizeList){
            positionLastPriority += 2;
            if (positionLastPriority == sizeList-1){
                normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), positionLastPriority));
            } else {
                normalQueuePatientService.updateQueueBecausePriority(normalQueuePatientList, positionLastPriority);
                normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), positionLastPriority));
            }
            return ResponseEntity.status(HttpStatus.OK).body("Paciente entrou na fila e sua posição é: " + positionLastPriority);
        }
        //A prioridade é o último da lista
        if (positionLastPriority == sizeList){
            positionLastPriority += 1;
            normalQueuePatientService.saveNormalQueuePatient(new NormalQueuePatientRecordDTO(normalQueue.getId(), patient.getId(), positionLastPriority));
        }

        return ResponseEntity.status(HttpStatus.OK).body("Paciente entrou na fila e sua posição é: " + positionLastPriority);
    }

}
