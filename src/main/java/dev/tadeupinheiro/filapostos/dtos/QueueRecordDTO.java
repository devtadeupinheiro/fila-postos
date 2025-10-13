package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QueueRecordDTO(
        @NotNull Long idQueue,
        @NotBlank String patientSusNumber
) {
}
