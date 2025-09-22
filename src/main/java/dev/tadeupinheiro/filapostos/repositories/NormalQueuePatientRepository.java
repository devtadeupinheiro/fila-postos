package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalQueuePatientRepository extends JpaRepository<NormalQueuePatient, Long> {
}
