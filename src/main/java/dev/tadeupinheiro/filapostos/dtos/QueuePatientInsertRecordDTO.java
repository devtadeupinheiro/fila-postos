package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QueuePatientInsertRecordDTO(
        @NotNull Long idQueue,
        @NotBlank String patientSusNumber
) {
}
