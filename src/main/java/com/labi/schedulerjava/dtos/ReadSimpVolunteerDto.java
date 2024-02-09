package com.labi.schedulerjava.dtos;

import com.labi.schedulerjava.enums.VolunteerOrigin;

import java.time.LocalDate;

public record ReadSimpVolunteerDto(
        Long id,
        String name,
        String lastName,
        String cpf,
        String phone,
        LocalDate birthDate,
        VolunteerOrigin origin
) {
}
