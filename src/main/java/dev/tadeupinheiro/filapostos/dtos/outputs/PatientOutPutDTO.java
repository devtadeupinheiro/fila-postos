package dev.tadeupinheiro.filapostos.dtos.outputs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PatientOutPutDTO {

    private String name;
    private String susNumber;
    private Integer age;
    private String priorityType;

    public PatientOutPutDTO() {}

    public PatientOutPutDTO(String name, String susNumber, Integer age, String priorityType) {
        this.name = name;
        this.susNumber = susNumber;
        this.age = age;
        this.priorityType = priorityType;
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
