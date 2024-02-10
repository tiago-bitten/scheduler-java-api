package com.labi.schedulerjava.dtos;

import com.labi.schedulerjava.enums.VolunteerOrigin;

import java.time.LocalDate;
import java.util.UUID;

public record ReadSimpVolunteerDto(
        Long id,
        UUID accessKey,
        String name,
        String lastName,
        String cpf,
        String phone,
        LocalDate birthDate,
        VolunteerOrigin origin
) {
}
