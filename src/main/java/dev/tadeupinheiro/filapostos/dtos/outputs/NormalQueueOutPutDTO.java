package dev.tadeupinheiro.filapostos.dtos.outputs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NormalQueueOutPutDTO {

    private LocalDate queueDay;
    private String specialy;

    public LocalDate getQueueDay() {
        return queueDay;
    }

    public void setQueueDay(LocalDate queueDay) {
        this.queueDay = queueDay;
    }

    public String getSpecialy() {
        return specialy;
    }

    public void setSpecialy(String specialy) {
        this.specialy = specialy;
    }

    @Override
    public String toString() {
        String date = queueDay.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "Fila: " + specialy + " Dia: " + date;
    }
}
