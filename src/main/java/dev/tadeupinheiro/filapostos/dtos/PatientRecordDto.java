package dev.tadeupinheiro.filapostos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientRecordDto(
        @NotBlank String name,
        @NotBlank String susNumber,
        @NotBlank Integer age,
        @NotNull Integer priorityTypeCode
) {
}
