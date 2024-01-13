package com.labi.schedulerjava.dtos;

import java.time.LocalDate;

public record ReadSimpVolunteerDto(
        Long id,
        String name,
        String lastName,
        String phone,
        LocalDate birthDate
) {
}
