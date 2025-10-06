package dev.tadeupinheiro.filapostos.dtos.outputs;

import dev.tadeupinheiro.filapostos.entities.DoctorType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NormalQueueOutPutDTO {

    private Long id;
    private String queueDay;
    private String specialy;
    private Integer quantityVacancies;

    public NormalQueueOutPutDTO(Long id, LocalDate queueDay, DoctorType specialy, Integer quantityVacancies) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.id = id;
        this.queueDay = queueDay.format(formatter);
        this.specialy = specialy.getSpecialy();
        this.quantityVacancies = quantityVacancies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getQuantityVacancies() {
        return quantityVacancies;
    }

    public void setQuantityVacancies(Integer quantityVacancies) {
        this.quantityVacancies = quantityVacancies;
    }

    public String toString() {
        return "Especialidade: " + specialy + " Dia: " + queueDay;
    }
}
