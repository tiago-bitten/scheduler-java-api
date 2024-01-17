package com.labi.schedulerjava.dtos;

import java.time.LocalDateTime;

public record CreateUnavailableDateDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        String rrule
) {
}
