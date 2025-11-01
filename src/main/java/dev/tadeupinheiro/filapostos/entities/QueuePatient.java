package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "tb_normal_queue_patient")
public class QueuePatient {

    @EmbeddedId
    private QueuePatientPK id = new QueuePatientPK();

    private Integer position;

    public QueuePatient() {}

    public QueuePatient(Integer position, Queue queue, Patient patient) {
        this.position = position;
        id.setPatient(patient);
        id.setNormalQueue(queue);
    }

    public QueuePatientPK getId() {
        return id;
    }

    public void setId(QueuePatientPK id) {
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
        QueuePatient that = (QueuePatient) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NormalQueuePatient{" +
                "id=" + id.getPatient().getSusNumber() +
                ", position=" + position +
                '}';
    }
}
