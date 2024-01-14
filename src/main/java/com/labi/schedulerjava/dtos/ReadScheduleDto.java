package com.labi.schedulerjava.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ReadScheduleDto(
        Long id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer weekNumber,
        List<ReadVolunteerMinistry> appointments
) {
}
