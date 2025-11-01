package dev.tadeupinheiro.filapostos.repositories;

import dev.tadeupinheiro.filapostos.entities.QueuePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueuePatientRepository extends JpaRepository<QueuePatient, Long> {

    @Modifying //Quando não for select, precisa usar o modifyng
    @Query(nativeQuery = true, value = "UPDATE tb_normal_queue_patient SET position = :newPosition WHERE normal_queue_id = :queueId AND patient_id = :patientId")
    void updateQueuePatientPosition(Long queueId, Long patientId, Integer newPosition);

    List<QueuePatient> findQueuePatientByIdPatientId(Long patientId);
}
