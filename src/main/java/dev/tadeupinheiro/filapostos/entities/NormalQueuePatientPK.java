package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class NormalQueuePatientPK {

    @ManyToOne
    @JoinColumn(name = "normal_queue_id")
    private NormalQueue normalQueue;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public NormalQueuePatientPK(){}

    public NormalQueuePatientPK(NormalQueue normalQueue, Patient patient) {
        this.normalQueue = normalQueue;
        this.patient = patient;
    }

    public NormalQueue getNormalQueue() {
        return normalQueue;
    }

    public void setNormalQueue(NormalQueue normalQueue) {
        this.normalQueue = normalQueue;
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
        NormalQueuePatientPK that = (NormalQueuePatientPK) o;
        return Objects.equals(getNormalQueue(), that.getNormalQueue()) && Objects.equals(getPatient(), that.getPatient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNormalQueue(), getPatient());
    }
}
