package com.labi.schedulerjava.dtos;

import java.time.LocalDate;

public record ReadVolunteersToAppointDto(
        Long id,
        Boolean isUnavailable,
        String name,
        String lastName,
        String cpf,
        String phone,
        LocalDate birthDate
) {
}
