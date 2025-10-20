package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "tb_normal_queue_patient")
public class NormalQueuePatient {

    @EmbeddedId
    private NormalQueuePatientPK id = new NormalQueuePatientPK();

    private Integer position;

    public NormalQueuePatient() {}

    public NormalQueuePatient(Integer position, NormalQueue normalQueue, Patient patient) {
        this.position = position;
        id.setPatient(patient);
        id.setNormalQueue(normalQueue);
    }

    public NormalQueuePatientPK getId() {
        return id;
    }

    public void setId(NormalQueuePatientPK id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalQueuePatient that = (NormalQueuePatient) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
