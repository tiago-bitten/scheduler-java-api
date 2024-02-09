package com.labi.schedulerjava.dtos;

import java.time.LocalDate;

public record SignInVolunteerDto(
        String cpf,
        LocalDate birthDate
) {
}
