package com.labi.schedulerjava.dtos;

public record ReadScaleDto(
        Long id,
        Long maxVolunteers,
        ReadSimpVolunteerDto volunteer,
        ReadMinistryDto ministry
) {
}
