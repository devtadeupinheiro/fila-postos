package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.QueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.QueuePatientInsertRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.outputs.QueuePatientOutPutDTO;
import dev.tadeupinheiro.filapostos.entities.QueuePatient;
import dev.tadeupinheiro.filapostos.services.QueueService;
import dev.tadeupinheiro.filapostos.services.PatientService;
import dev.tadeupinheiro.filapostos.services.QueuePatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/schedule-appointment")
public class QueuePatientController {

    @Autowired
    private QueuePatientService queuePatientService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private QueueService queueService;

    /* CÓDIGO DESNECESSÁRIO
    @PostMapping
    public ResponseEntity<String> savePatient(@RequestBody NormalQueuePatientRecordDTO normalQueuePatientRecordDTO) {
        return normalQueuePatientService.saveNormalQueuePatient(normalQueuePatientRecordDTO);
    }

     */

    @GetMapping
    public ResponseEntity<List<QueuePatient>> findAllQueuePatients() {
        return ResponseEntity.status(HttpStatus.OK).body(queuePatientService.findAllQueuePatient());
    }

    @GetMapping("/{patientSusNumber}")
    public ResponseEntity<List<QueuePatientOutPutDTO>> findQueueOfPatientById(@PathVariable String patientSusNumber) {
        if (patientSusNumber.length() != 15){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String regex = ".*[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(patientSusNumber);
        boolean matchFound = matcher.find();

        if (matchFound) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<QueuePatientOutPutDTO> queuePatientList = queuePatientService.findQueueOfPatientById(patientSusNumber);
        if (queuePatientList == null || queuePatientList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(queuePatientList);
    }

    @PostMapping
    public ResponseEntity<String> registerVacancy (@RequestBody @Valid QueuePatientInsertRecordDTO queuePatientInsertRecordDTO) {

        var queue = queueService.findQueueWithVacanciesById(queuePatientInsertRecordDTO.idQueue());
        var patient = patientService.findBySusNumber(queuePatientInsertRecordDTO.patientSusNumber());

        if (queue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar essa fila");
        }
        //Consulta se a lista tem vagas
        if (queue.getQuantityVacancies() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Não tem mais vaga nessa fila");
        }
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar esse paciente");
        }

        //Cria uma lista com todos os pacientes que estão na fila passada como argumento no json
        List<QueuePatient> queuePatientList = queuePatientService.findAllQueuePatient()
                .stream()
                .filter(QueuePatient -> QueuePatient.getId().getNormalQueue().getId().equals(queue.getId()))
                .toList();

        //Se a lista está vazia, quer dizer que não há pacientes na fila, portanto, cadastro como posição 1.
        if (queuePatientList.isEmpty()){
            boolean returnSave = queuePatientService.saveNormalQueuePatient(new QueuePatientRecordDTO(queue.getId(), patient.getId(), 1));
            if (returnSave){
                return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: 1");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente ou Fila não encontrada");
            }
        }

        //Se chegou aqui, a lista não está vazia.
        //Confere se paciente já está na fila
        if (queuePatientList.stream().anyMatch(p -> p.getId().getPatient().getSusNumber().equalsIgnoreCase(queuePatientInsertRecordDTO.patientSusNumber()))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Paciente já está na fila");
        }

        //Aqui o paciente não tem prioridade
        if (patient.getPriorityType().equalsIgnoreCase("NORMAL")){
            int sizeList = queuePatientList.size();
            ++sizeList;
            queuePatientService.saveNormalQueuePatient(new QueuePatientRecordDTO(queue.getId(), patient.getId(), sizeList));
            return ResponseEntity.status(HttpStatus.CREATED).body("Paciente entrou na fila e sua posição é: " + sizeList);
        }

        //Aqui o paciente tem prioridade
        int positionLastPriority = 0;
        int sizeList = queuePatientList.size();
        for (QueuePatient queuePatient : queuePatientList){
            if (!queuePatient.getId().getPatient().getPriorityType().equalsIgnoreCase("NORMAL")){
                positionLastPriority = queuePatient.getPosition(); //Define qual a posição da última prioridade
            }
        }

        //Não tem na lista nenhum prioridade ou a prioridade não é o último da lista
        if (positionLastPriority < sizeList){
            positionLastPriority += 2;
            if (positionLastPriority == sizeList-1){
                queuePatientService.saveNormalQueuePatient(new QueuePatientRecordDTO(queue.getId(), patient.getId(), positionLastPriority));
            } else {
                queuePatientService.updateQueueBecausePriority(queuePatientList, positionLastPriority);
                queuePatientService.saveNormalQueuePatient(new QueuePatientRecordDTO(queue.getId(), patient.getId(), positionLastPriority));
            }
            return ResponseEntity.status(HttpStatus.OK).body("Paciente entrou na fila e sua posição é: " + positionLastPriority);
        }
        //A prioridade é o último da lista
        if (positionLastPriority == sizeList){
            positionLastPriority += 1;
            queuePatientService.saveNormalQueuePatient(new QueuePatientRecordDTO(queue.getId(), patient.getId(), positionLastPriority));
        }

        return ResponseEntity.status(HttpStatus.OK).body("Paciente entrou na fila e sua posição é: " + positionLastPriority);
    }

}
