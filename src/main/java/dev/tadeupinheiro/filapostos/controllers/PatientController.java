package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.PatientRecordDto;
import dev.tadeupinheiro.filapostos.entities.Patient;
import dev.tadeupinheiro.filapostos.entities.PriorityTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.tadeupinheiro.filapostos.services.PatientService;

import java.util.List;

@RestController
@RequestMapping(value = "/register")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<String> savePatient (@RequestBody PatientRecordDto patientRecordDto) {
        boolean susNumberOk = patientRecordDto.susNumber().length() == 15;
        if (!susNumberOk) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O número do SUS precisa ter 15 dígitos e caracteres numéricos");
        }
        if (patientRecordDto.name().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo nome precisa ser preenchido");
        }
        if (patientRecordDto.age() > 115) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Precisa preencher uma idade válida");
        }
        if (patientService.existsBySusNumber(patientRecordDto.susNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Número do SUS já cadastrado");
        }

        var patient = new Patient();
        BeanUtils.copyProperties(patientRecordDto, patient);
        if (patientRecordDto.age() >= 60) {
            patient.setPriorityType(PriorityTypeEnum.IDOSO.toString());
        }

        if (patientRecordDto.priorityTypeCode() > 0 && patientRecordDto.priorityTypeCode() <= 8 && patientRecordDto.priorityTypeCode() != 5) {
            switch (patientRecordDto.priorityTypeCode()) {
                case 1: patient.setPriorityType(PriorityTypeEnum.GESTANTE.toString()); break;
                case 2: patient.setPriorityType(PriorityTypeEnum.CRIANCA_COLO.toString()); break;
                case 3: patient.setPriorityType(PriorityTypeEnum.LACTANTE.toString()); break;
                case 4: patient.setPriorityType(PriorityTypeEnum.AUTISTA.toString()); break;
                case 6: patient.setPriorityType(PriorityTypeEnum.DEFICIENTE.toString()); break;
                case 7: patient.setPriorityType(PriorityTypeEnum.OBESOS.toString()); break;
                case 8: patient.setPriorityType(PriorityTypeEnum.DOADOR_SANGUE.toString()); break;
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de prioridade incorreto");
        }

        patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente criado com sucesso");
    }

    @GetMapping
    public List<Patient> findAllPatients (){
        return patientService.findAll();
    }

}
