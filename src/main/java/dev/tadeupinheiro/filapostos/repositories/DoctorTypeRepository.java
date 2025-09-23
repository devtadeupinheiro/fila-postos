package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.DoctorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorTypeRepository extends JpaRepository<DoctorType, Long> {

    public boolean existsDoctorTypeBySpecialy(String specialy);

    public Optional<DoctorType> findDoctorTypeBySpecialy(String specialy);

}
