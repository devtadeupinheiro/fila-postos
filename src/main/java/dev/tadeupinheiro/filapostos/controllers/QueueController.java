package dev.tadeupinheiro.filapostos.controllers;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.QueueRecordDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import dev.tadeupinheiro.filapostos.entities.Patient;
import dev.tadeupinheiro.filapostos.services.DoctorTypeService;
import dev.tadeupinheiro.filapostos.services.NormalQueuePatientService;
import dev.tadeupinheiro.filapostos.services.NormalQueueService;
import dev.tadeupinheiro.filapostos.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/schedule-appointment")
public class QueueController {

    @Autowired
    private NormalQueuePatientService normalQueuePatientService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorTypeService doctorTypeService;
    @Autowired
    private NormalQueueService normalQueueService;

    @PostMapping
    public ResponseEntity<String> registerVacancy (@RequestBody QueueRecordDTO queueRecordDTO) {
        if (!patientService.existsBySusNumber(queueRecordDTO.patientSusNumber())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
        }
        if (!doctorTypeService.existsBySpecialy(queueRecordDTO.specialy())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialidade não encontrada");
        }
        var patient = new Patient();
        patient = patientService.findBySusNumber(queueRecordDTO.patientSusNumber());
        //var specialy = new DoctorType();
        //specialy = doctorTypeService.findBySpecialy(queueRecordDTO.specialy());

        long quantityPatientBySpecialy = 0;
        if (patient.getPriorityType().equalsIgnoreCase("NORMAL")) {
            List<NormalQueue> normalQueueList = normalQueueService.findAll();
            String sp = queueRecordDTO.specialy();
            Optional<NormalQueue> list = normalQueueList.stream().filter(l -> l.getDoctorType().getSpecialy().equalsIgnoreCase(sp)).findFirst();
            if (list.isPresent()){
                List<NormalQueuePatient> normalQueuePatientList = normalQueuePatientService.findAllNormalQueuePatient();
                boolean existsPatientInQueue =  normalQueuePatientList.stream().anyMatch(p -> p.getId().getPatient().getSusNumber().equalsIgnoreCase(queueRecordDTO.patientSusNumber()));
                if (existsPatientInQueue){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Paciente já está cadastrado nesta fila");
                }
                quantityPatientBySpecialy = normalQueuePatientList.stream().filter(l -> l.getId().getNormalQueue().getDoctorType().getSpecialy().equalsIgnoreCase(sp)).count();
                System.out.println(quantityPatientBySpecialy);
                quantityPatientBySpecialy += 1;
                var normalQueuePatientRecordDto = new NormalQueuePatientRecordDTO(list.get().getId(), patient.getId(), Integer.valueOf(String.valueOf(quantityPatientBySpecialy)));
                normalQueuePatientService.saveNormalQueuePatient(normalQueuePatientRecordDto);
                /*
                impedir de o paciente entrar mais de uma vez na mesma fila
                 */
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fila de pacientes não prioritários para a especialidade " + queueRecordDTO.specialy().toUpperCase() + " não encontrada");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FALTA CRIAR LÓGICA PARA OS PACIENTES PRIORITÁRIOS");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente registrado com sucesso. Sua posição na fila é: " + quantityPatientBySpecialy);
    }

}
