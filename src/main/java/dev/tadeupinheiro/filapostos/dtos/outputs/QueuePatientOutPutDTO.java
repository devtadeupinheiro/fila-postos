package dev.tadeupinheiro.filapostos.dtos.outputs;

public class QueuePatientOutPutDTO {

    private String queueDay;
    private String specialy;
    private Integer position;

    public QueuePatientOutPutDTO(){}

    public QueuePatientOutPutDTO(String queueDay, String specialy, Integer position) {
        this.queueDay = queueDay;
        this.specialy = specialy;
        this.position = position;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
