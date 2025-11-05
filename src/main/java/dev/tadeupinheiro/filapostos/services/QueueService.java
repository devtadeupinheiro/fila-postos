package dev.tadeupinheiro.filapostos.services;

import dev.tadeupinheiro.filapostos.dtos.QueueRecordDto;
import dev.tadeupinheiro.filapostos.entities.DoctorType;
import dev.tadeupinheiro.filapostos.entities.Queue;
import dev.tadeupinheiro.filapostos.entities.QueuePatient;
import dev.tadeupinheiro.filapostos.repositories.DoctorTypeRepository;
import dev.tadeupinheiro.filapostos.repositories.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private DoctorTypeRepository doctorTypeRepository;
    @Autowired
    private QueuePatientService queuePatientService;

    @Transactional
    public ResponseEntity<String> saveQueue(QueueRecordDto queueRecordDto) {
        Optional<DoctorType> doctorType = doctorTypeRepository.findById(queueRecordDto.doctorTypeId());
        List<Queue> queueList = findAll().stream().filter(normalQueue -> normalQueue.getDoctorType().getId() == queueRecordDto.doctorTypeId() && normalQueue.getDay().isEqual(LocalDate.parse(queueRecordDto.queueDay()))).toList();
        boolean doctorTypeIsPresent = doctorType.isPresent();
        if(!doctorTypeIsPresent){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialidade não encontrada");
        }
        if(!queueList.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe fila nessa especialidade para esta data");
        }
        var normalQueue = new Queue();
        normalQueue.setDoctorType(doctorType.get());
        normalQueue.setDay(LocalDate.parse(queueRecordDto.queueDay()));
        normalQueue.setQuantityVacancies(queueRecordDto.quantityVacancies());
        queueRepository.save(normalQueue);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fila criada com sucesso");
    }

    @Transactional(readOnly = true)
    public List<Queue> findAll() {
        return queueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Queue> findQueueWithVacancies (){
        List<QueuePatient> queuePatientList = queuePatientService.findAllQueuePatient();
        List<Queue> queueList = findAll();
        int quantityPatientByList = 0;
        for (Queue queue : queueList) {
            for (QueuePatient queuePatient : queuePatientList){
                if (Objects.equals(queue.getId(), queuePatient.getId().getNormalQueue().getId())){
                    quantityPatientByList++;
                }
            }
            queue.setQuantityVacancies(queue.getQuantityVacancies()-quantityPatientByList);
        }
        return queueList;
    }

    @Transactional(readOnly = true)
    public Queue findQueueWithVacanciesById (Long queueId){
        List<QueuePatient> queuePatientList = queuePatientService.findAllQueuePatient();
        var queue = findQueueById(queueId);

        int quantityPatientByList = 0;
        for (QueuePatient queuePatient : queuePatientList){
            if (Objects.equals(queueId, queuePatient.getId().getNormalQueue().getId())){
                quantityPatientByList++;
            }
        }
        queue.setQuantityVacancies(queue.getQuantityVacancies()-quantityPatientByList);
        return queue;
    }

    @Transactional(readOnly = true)
    public Queue findQueueById(Long id) {
        return queueRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteQueue(Long id) {
        if(queueRepository.existsById(id)){
            queueRepository.deleteById(id);
            return true;
        }
        return false;
    }

}