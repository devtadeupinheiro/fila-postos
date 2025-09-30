package dev.tadeupinheiro.filapostos.dtos;

import java.time.LocalDate;

public record NormalQueueRecordDto(
        String queueDay,
        Long doctorTypeId,
        Integer quantityVacancies
) {
}
