package dev.tadeupinheiro.filapostos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "sus_number")
    private String susNumber;
    private Integer age;
    private String priorityType;

    public Patient() {}

    public Patient(String name, String susNumber, Integer age) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSusNumber() {
        return susNumber;
    }

    public void setSusNumber(String susNumber) {
        this.susNumber = susNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }
}
