package com.labi.schedulerjava.dtos;

import java.time.LocalDate;
import java.util.List;

public record ReadScheduleDto(
        Long id,
        String name,
        String description,
        LocalDate date,
        List<ReadSimpVolunteerMinistry> volunteerMinistries
) {
}
