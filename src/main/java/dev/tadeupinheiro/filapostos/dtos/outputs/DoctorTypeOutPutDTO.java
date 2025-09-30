package dev.tadeupinheiro.filapostos.dtos.outputs;

public class DoctorTypeOutPutDTO {

    private String specialy;

    public DoctorTypeOutPutDTO() {}

    public DoctorTypeOutPutDTO(String specialy) {
        this.specialy = specialy;
    }

    public String getSpecialy() {
        return specialy;
    }

    public void setSpecialy(String specialy) {
        this.specialy = specialy;
    }

    public String toString() {
        return "Especialidade:" + specialy;
    }
}
