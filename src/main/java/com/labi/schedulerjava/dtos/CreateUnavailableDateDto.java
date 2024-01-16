package com.labi.schedulerjava.dtos;

import java.time.LocalDate;

public record CreateUnavailableDateDto(
        LocalDate date,
        String rrule
) {
}
