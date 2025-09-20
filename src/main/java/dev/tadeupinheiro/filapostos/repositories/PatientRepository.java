package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    public boolean existsBySusNumber(String susNumber);
}
