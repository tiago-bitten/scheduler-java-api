package com.labi.schedulerjava.dtos;

public record ReadVolunteerMinistry(
        Long id,
        ReadSimpVolunteerDto volunteer,
        ReadMinistryDto ministry
) {
}
