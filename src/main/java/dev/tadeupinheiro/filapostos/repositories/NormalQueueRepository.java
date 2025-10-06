package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.DoctorType;
import dev.tadeupinheiro.filapostos.entities.NormalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NormalQueueRepository extends JpaRepository<NormalQueue, Long> {

    Optional<NormalQueue> findNormalQueueByDoctorType (DoctorType doctorType);

}
