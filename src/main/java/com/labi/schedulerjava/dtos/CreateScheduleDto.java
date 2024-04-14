package com.labi.schedulerjava.dtos;

import java.time.LocalDateTime;

public record CreateScheduleDto(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
