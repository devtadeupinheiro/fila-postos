package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotNull;

public record QueueRecordDTO(
        @NotNull String patientSusNumber,
        @NotNull String specialy
) {
}
