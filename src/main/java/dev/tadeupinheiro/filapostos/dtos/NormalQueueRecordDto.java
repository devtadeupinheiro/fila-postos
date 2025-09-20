package dev.tadeupinheiro.filapostos.dtos;

import java.time.LocalDate;

public record NormalQueueRecordDto(
        LocalDate day,
        Long doctorTypeId
) {
}
