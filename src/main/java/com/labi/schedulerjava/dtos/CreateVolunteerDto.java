package com.labi.schedulerjava.dtos;

import java.time.LocalDate;

public record CreateVolunteerDto(
        String name,
        String lastName,
        String phone,
        LocalDate birthDate
) {
}
