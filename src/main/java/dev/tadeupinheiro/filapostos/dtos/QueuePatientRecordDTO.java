package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotNull;

public record QueuePatientRecordDTO(
        @NotNull Long normalQueueId,
        @NotNull Long patientId,
        @NotNull Integer position
) {
}
