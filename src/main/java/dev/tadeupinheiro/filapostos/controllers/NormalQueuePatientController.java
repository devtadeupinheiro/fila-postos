package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.outputs.NormalQueuePatientOutPutDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import dev.tadeupinheiro.filapostos.services.NormalQueuePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/queuePatient")
public class NormalQueuePatientController {

    @Autowired
    private NormalQueuePatientService normalQueuePatientService;

    /* MÉTODO DESNECESSÁRIO
    @PostMapping
    public ResponseEntity<String> savePatient(@RequestBody NormalQueuePatientRecordDTO normalQueuePatientRecordDTO) {
        return normalQueuePatientService.saveNormalQueuePatient(normalQueuePatientRecordDTO);
    }

     */

    @GetMapping
    public ResponseEntity<List<NormalQueuePatient>> findAllNormalQueuePatients() {
        return ResponseEntity.status(HttpStatus.OK).body(normalQueuePatientService.findAllNormalQueuePatient());
    }

    @GetMapping("/{patientSusNumber}")
    public ResponseEntity<List<NormalQueuePatientOutPutDTO>> findQueueOfPatientById(@PathVariable String patientSusNumber) {
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

        List<NormalQueuePatientOutPutDTO> normalQueuePatientList = normalQueuePatientService.findQueueOfPatientById(patientSusNumber);
        if (normalQueuePatientList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(normalQueuePatientList);
    }

}
