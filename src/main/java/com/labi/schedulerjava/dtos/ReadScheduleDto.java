package com.labi.schedulerjava.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReadScheduleDto(
        Long id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer weekNumber,
        List<ReadSimpVolunteerMinistry> volunteerMinistries
) {
}
