package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QueueRecordDto(
        @NotBlank String queueDay,
        @NotNull Long doctorTypeId,
        @NotNull Integer quantityVacancies
) {
}
