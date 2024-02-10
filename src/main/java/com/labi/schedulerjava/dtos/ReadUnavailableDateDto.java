package com.labi.schedulerjava.dtos;

import java.time.LocalDateTime;

public record ReadUnavailableDateDto(
        Long id,
        Long volunteerId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String rrule
) {
}
