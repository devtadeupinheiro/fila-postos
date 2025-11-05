package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.DoctorType;
import dev.tadeupinheiro.filapostos.entities.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findQueueByDoctorType (DoctorType doctorType);

    Optional<Queue> findQueueById(Long id);

}
