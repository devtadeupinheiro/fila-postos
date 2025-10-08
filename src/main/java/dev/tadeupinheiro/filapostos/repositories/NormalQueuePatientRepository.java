package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NormalQueuePatientRepository extends JpaRepository<NormalQueuePatient, Long> {

    Optional<NormalQueuePatient> findByNormalQueuePatientId(Long idNormalQueuePatient);
}
