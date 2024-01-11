package com.labi.schedulerjava.dtos;

import java.time.LocalDate;
import java.util.List;

public record ReadScheduleDto(
        Long id,
        LocalDate date,
        Boolean current,
        List<ReadSimpVolunteerMinistry> volunteerMinistries
) {
}
