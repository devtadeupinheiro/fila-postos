package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_doctor_type")
public class DoctorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specialy;

    public DoctorType() {}

    public DoctorType(String specialy) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialy() {
        return specialy;
    }

    public void setSpecialy(String specialy) {
        this.specialy = specialy;
    }
}
