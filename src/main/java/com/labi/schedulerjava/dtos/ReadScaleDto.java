package com.labi.schedulerjava.dtos;

import java.util.List;

public record ReadScaleDto(
        Long id,
        Long maxVolunteers,
        ReadSimpScheduleDto schedule,
        List<ReadSimpVolunteerDto> selectedVolunteers
) {
}
