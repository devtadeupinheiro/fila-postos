package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import dev.tadeupinheiro.filapostos.repositories.NormalQueuePatientRepository;
import dev.tadeupinheiro.filapostos.services.NormalQueuePatientService;
import jakarta.persistence.GeneratedValue;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/insertQueuePatient")
public class NormalQueuePatientController {

    @Autowired
    private NormalQueuePatientService normalQueuePatientService;

    @PostMapping
    public ResponseEntity<String> savePatient(@RequestBody NormalQueuePatientRecordDTO normalQueuePatientRecordDTO) {
        return normalQueuePatientService.saveNormalQueuePatient(normalQueuePatientRecordDTO);
    }

    @GetMapping
    public ResponseEntity<List<NormalQueuePatient>> findAllNormalQueuePatients() {
        return ResponseEntity.status(HttpStatus.OK).body(normalQueuePatientService.findAllNormalQueuePatient());
    }

}
