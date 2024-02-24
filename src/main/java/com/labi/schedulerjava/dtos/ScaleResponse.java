package com.labi.schedulerjava.dtos;

public record ScaleResponse(
        Long id,
        Long numberOfVolunteers,
        ReadSimpVolunteerDto volunteer,
        ReadMinistryDto ministry,
        ActivityResponse activity
) {
}
