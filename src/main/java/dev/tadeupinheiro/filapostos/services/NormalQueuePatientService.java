package dev.tadeupinheiro.filapostos.services;

import dev.tadeupinheiro.filapostos.dtos.NormalQueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import dev.tadeupinheiro.filapostos.repositories.NormalQueuePatientRepository;
import dev.tadeupinheiro.filapostos.repositories.NormalQueueRepository;
import dev.tadeupinheiro.filapostos.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NormalQueuePatientService {

    @Autowired
    private NormalQueuePatientRepository normalQueuePatientRepository;
    @Autowired
    private NormalQueueRepository normalQueueRepository;
    @Autowired
    private PatientRepository patientRepository;


    @Transactional
    public ResponseEntity<String> saveNormalQueuePatient(NormalQueuePatientRecordDTO normalQueuePatientRecordDTO) {
        var normalQueue = normalQueueRepository.findById(normalQueuePatientRecordDTO.normalQueueId());
        var patient = patientRepository.findById(normalQueuePatientRecordDTO.patientId());
        if (patient.isEmpty() || normalQueue.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fila normal ou Paciente não encontrado");
        }
        var normalQueuePatient = new NormalQueuePatient(normalQueuePatientRecordDTO.position(), normalQueue.get(), patient.get());
        normalQueuePatientRepository.save(normalQueuePatient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente inserido na fila normal da especialidade: " + normalQueue.get().getDoctorType().getSpecialy());
    }

    @Transactional(readOnly = true)
    public List<NormalQueuePatient> findAllNormalQueuePatient (){
        return normalQueuePatientRepository.findAll();
    }

    @Transactional
    public void updateQueueBecausePriority (List<NormalQueuePatient> normalQueuePatientList, int initialIndex) {

        //Filter garante que somente os objetos modificados constaram na lista, evitando querys desnecessárias
        //Map garante que cada objeto que satisfaça a condição seja incrementado
        normalQueuePatientList.stream().filter(p -> p.getPosition() >= initialIndex).forEach(p -> p.setPosition(p.getPosition()+1));

        for (int i = 0; i < normalQueuePatientList.size(); i++) {
            NormalQueuePatient normalQueuePatient = normalQueuePatientList.get(i);
            normalQueuePatient.toString();
            normalQueuePatientRepository.updateNormalQueuePatientPosition(normalQueuePatient.getId().getNormalQueue().getId(), normalQueuePatient.getId().getPatient().getId(), normalQueuePatient.getPosition());
        }
    }

}
