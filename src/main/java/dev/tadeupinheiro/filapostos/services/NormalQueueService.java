package dev.tadeupinheiro.filapostos.services;

import dev.tadeupinheiro.filapostos.dtos.NormalQueueRecordDto;
import dev.tadeupinheiro.filapostos.entities.DoctorType;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import dev.tadeupinheiro.filapostos.repositories.DoctorTypeRepository;
import dev.tadeupinheiro.filapostos.repositories.NormalQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NormalQueueService {

    @Autowired
    private NormalQueueRepository normalQueueRepository;

    @Autowired
    private DoctorTypeRepository doctorTypeRepository;

    @Transactional
    public ResponseEntity<String> saveNormalQueue(NormalQueueRecordDto normalQueueRecordDto) {
        Optional<DoctorType> doctorType = doctorTypeRepository.findById(normalQueueRecordDto.doctorTypeId());
        List<NormalQueue> normalQueueList = findAll().stream().filter(normalQueue -> normalQueue.getDoctorType().getId() == normalQueueRecordDto.doctorTypeId() && normalQueue.getDay().isEqual(LocalDate.parse(normalQueueRecordDto.queueDay()))).toList();
        boolean doctorTypeIsPresent = doctorType.isPresent();
        if(!doctorTypeIsPresent){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialidade não encontrada");
        }
        if(!normalQueueList.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe fila nessa especialidade para esta data");
        }
        var normalQueue = new NormalQueue();
        normalQueue.setDoctorType(doctorType.get());
        normalQueue.setDay(LocalDate.parse(normalQueueRecordDto.queueDay()));
        normalQueue.setQuantityVacancies(normalQueueRecordDto.quantityVacancies());
        normalQueueRepository.save(normalQueue);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fila criada com sucesso");
    }

    @Transactional(readOnly = true)
    public List<NormalQueue> findAll() {
        return normalQueueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NormalQueue findNormalQueueById(Long id) {
        return normalQueueRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteNormalQueue(Long id) {
        if(normalQueueRepository.existsById(id)){
            normalQueueRepository.deleteById(id);
            return true;
        }
        return false;
    }

}