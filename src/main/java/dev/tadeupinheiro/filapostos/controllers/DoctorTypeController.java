package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.entities.DoctorType;
import dev.tadeupinheiro.filapostos.services.DoctorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/specialy")
public class DoctorTypeController {

    @Autowired
    private DoctorTypeService doctorTypeService;

    @PostMapping
    public ResponseEntity<String> saveDoctorType (@RequestBody DoctorType doctorType) {
        System.out.println(doctorType.toString());
        if (doctorTypeService.existsBySpecialy(doctorType.getSpecialy())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Especialidade já criada");
        }
        doctorTypeService.saveDoctorType(doctorType);
        return ResponseEntity.status(HttpStatus.CREATED).body("Especialidade criada com sucesso");
    }

    @GetMapping
    public List<DoctorType> findAllDoctorTypes () {
        return doctorTypeService.findAll();
    }

}
