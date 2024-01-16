package com.labi.schedulerjava.dtos;

public record ReadVolunteerMinistry(
        Long id,
        Boolean isActive,
        ReadSimpVolunteerDto volunteer,
        ReadMinistryDto ministry
) {
}
