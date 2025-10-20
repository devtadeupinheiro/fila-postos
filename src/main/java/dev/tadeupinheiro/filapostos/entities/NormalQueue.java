package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_normal_queue")
public class NormalQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "queue_day")
    private LocalDate day;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorType doctorType;
    private Integer quantityVacancies;

    public NormalQueue() {}

    public NormalQueue(LocalDate day, DoctorType doctorType, Integer quantityVacancies) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public DoctorType getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(DoctorType doctorType) {
        this.doctorType = doctorType;
    }

    public Integer getQuantityVacancies() {
        return quantityVacancies;
    }

    public void setQuantityVacancies(Integer quantityVacancies) {
        this.quantityVacancies = quantityVacancies;
    }

    @Override
    public String toString() {
        return "NormalQueue{" +
                "id=" + id +
                ", day=" + day +
                ", doctorType=" + doctorType +
                ", quantityVacancies=" + quantityVacancies +
                '}';
    }
}
