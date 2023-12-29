package com.labi.schedulerjava.dtos;

import java.time.LocalDate;

public record CreateScheduleDto(
        String name,
        String description,
        LocalDate date
) {
}
