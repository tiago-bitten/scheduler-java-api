package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateVolunteerDto(

        @NotBlank(message = "O nome do voluntário é obrigatório")
        String name,
        String lastName,
        String cpf,
        String phone,
        LocalDate birthDate
) {
}
