package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NormalQueueRecordDto(
        @NotBlank String queueDay,
        @NotNull Long doctorTypeId,
        @NotNull Integer quantityVacancies
) {
}
