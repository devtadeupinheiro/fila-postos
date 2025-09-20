package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.DoctorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorTypeRepository extends JpaRepository<DoctorType, Long> {

    public boolean existsDoctorTypeBySpecialy(String specialy);

}
