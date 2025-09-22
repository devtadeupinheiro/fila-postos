package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotNull;

public record NormalQueuePatientRecordDTO(
        @NotNull Long normalQueueId,
        @NotNull Long patientId,
        @NotNull Integer position
) {
}
