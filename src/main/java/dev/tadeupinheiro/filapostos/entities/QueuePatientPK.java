package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class QueuePatientPK {

    @ManyToOne
    @JoinColumn(name = "normal_queue_id")
    private Queue queue;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public QueuePatientPK(){}

    public QueuePatientPK(Queue queue, Patient patient) {
        this.queue = queue;
        this.patient = patient;
    }

    public Queue getNormalQueue() {
        return queue;
    }

    public void setNormalQueue(Queue queue) {
        this.queue = queue;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueuePatientPK that = (QueuePatientPK) o;
        return Objects.equals(getNormalQueue(), that.getNormalQueue()) && Objects.equals(getPatient(), that.getPatient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNormalQueue(), getPatient());
    }
}
