package dev.tadeupinheiro.filapostos.dtos.outputs;

import dev.tadeupinheiro.filapostos.entities.DoctorType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NormalQueueOutPutDTO {

    private String queueDay;
    private String specialy;

    public NormalQueueOutPutDTO(LocalDate queueDay, DoctorType specialy) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.queueDay = queueDay.format(formatter);
        this.specialy = specialy.getSpecialy();
    }

    public String getQueueDay() {
        return queueDay;
    }

    public void setQueueDay(String queueDay) {
        this.queueDay = queueDay;
    }

    public String getSpecialy() {
        return specialy;
    }

    public void setSpecialy(String specialy) {
        this.specialy = specialy;
    }

    public String toString() {
        return "Especialidade: " + specialy + " Dia: " + queueDay;
    }
}
