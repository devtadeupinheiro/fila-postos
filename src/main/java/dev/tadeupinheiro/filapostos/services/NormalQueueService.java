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

@Service
public class NormalQueueService {

    @Autowired
    private NormalQueueRepository normalQueueRepository;

    @Autowired
    private DoctorTypeRepository doctorTypeRepository;

    @Transactional
    public ResponseEntity<String> saveNormalQueue(NormalQueueRecordDto normalQueueRecordDto) {
        Optional<DoctorType> doctorType = doctorTypeRepository.findById(normalQueueRecordDto.doctorTypeId());
        if (doctorType.isPresent()) {
            var normalQueue = new NormalQueue();
            normalQueue.setDoctorType(doctorType.get());
            normalQueue.setDay(LocalDate.parse(normalQueueRecordDto.queueDay()));
            normalQueue.setQuantityVacancies(normalQueueRecordDto.quantityVacancies());
            normalQueueRepository.save(normalQueue);
            return ResponseEntity.status(HttpStatus.CREATED).body("Fila criada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialidade não encontrada");
        }
    }

    @Transactional(readOnly = true)
    public List<NormalQueue> findAll() {
        return normalQueueRepository.findAll();
    }

}