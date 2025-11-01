package dev.tadeupinheiro.filapostos.services;

import dev.tadeupinheiro.filapostos.dtos.QueuePatientRecordDTO;
import dev.tadeupinheiro.filapostos.dtos.outputs.QueuePatientOutPutDTO;
import dev.tadeupinheiro.filapostos.entities.QueuePatient;
import dev.tadeupinheiro.filapostos.entities.Patient;
import dev.tadeupinheiro.filapostos.repositories.QueuePatientRepository;
import dev.tadeupinheiro.filapostos.repositories.QueueRepository;
import dev.tadeupinheiro.filapostos.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueuePatientService {

    @Autowired
    private QueuePatientRepository queuePatientRepository;
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private PatientRepository patientRepository;


    @Transactional
    public boolean saveNormalQueuePatient(QueuePatientRecordDTO queuePatientRecordDTO) {
        var normalQueue = queueRepository.findById(queuePatientRecordDTO.normalQueueId());
        var patient = patientRepository.findById(queuePatientRecordDTO.patientId());
        boolean returnSave;
        if (patient.isEmpty() || normalQueue.isEmpty()) {
            return returnSave = false;
        }
        var normalQueuePatient = new QueuePatient(queuePatientRecordDTO.position(), normalQueue.get(), patient.get());
        queuePatientRepository.save(normalQueuePatient);
        return returnSave = true;
    }

    @Transactional(readOnly = true)
    public List<QueuePatient> findAllQueuePatient (){
        return queuePatientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<QueuePatientOutPutDTO> findQueueOfPatientById (String patientSusNumber){
        Optional<Patient> patientOptional= patientRepository.findBySusNumber(patientSusNumber);
        if (patientOptional.isEmpty()) {
            return null;
        }
        List<QueuePatient> queuePatientList = queuePatientRepository.findQueuePatientByIdPatientId(patientOptional.get().getId());
        if (queuePatientList == null || queuePatientList.isEmpty()) {
            return null;
        } else {
            List<QueuePatientOutPutDTO> queuePatientOutPutDTOList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (QueuePatient queuePatient : queuePatientList) {
                var normalQueuePatientOutPutDTO = new QueuePatientOutPutDTO();
                normalQueuePatientOutPutDTO.setQueueDay(queuePatient.getId().getNormalQueue().getDay().format(formatter));
                normalQueuePatientOutPutDTO.setSpecialy(queuePatient.getId().getNormalQueue().getDoctorType().getSpecialy());
                normalQueuePatientOutPutDTO.setPosition(queuePatient.getPosition());
                queuePatientOutPutDTOList.add(normalQueuePatientOutPutDTO);
            }
            return queuePatientOutPutDTOList;
        }
    }

    @Transactional
    public void updateQueueBecausePriority (List<QueuePatient> queuePatientList, int initialIndex) {

        //Filter garante que somente os objetos modificados constaram na lista, evitando querys desnecessárias
        //Map garante que cada objeto que satisfaça a condição seja incrementado
        queuePatientList.stream().filter(p -> p.getPosition() >= initialIndex).forEach(p -> p.setPosition(p.getPosition()+1));

        for (int i = 0; i < queuePatientList.size(); i++) {
            QueuePatient queuePatient = queuePatientList.get(i);
            queuePatient.toString();
            queuePatientRepository.updateQueuePatientPosition(queuePatient.getId().getNormalQueue().getId(), queuePatient.getId().getPatient().getId(), queuePatient.getPosition());
        }
    }

}
