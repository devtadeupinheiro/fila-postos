package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.NormalQueuePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalQueuePatientRepository extends JpaRepository<NormalQueuePatient, Long> {

    @Modifying //Quando não for select, precisa usar o modifyng
    @Query(nativeQuery = true, value = "UPDATE tb_normal_queue_patient SET position = :newPosition WHERE normal_queue_id = :queueId AND patient_id = :patientId")
    void updateNormalQueuePatientPosition(Long queueId, Long patientId, Integer newPosition);

}
