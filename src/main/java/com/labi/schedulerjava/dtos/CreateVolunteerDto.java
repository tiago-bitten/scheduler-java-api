package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateVolunteerDto(

        @NotBlank(message = "O nome do voluntário é obrigatório")
        String name,
        String lastName,

        @NotBlank(message = "O CPF do voluntário é obrigatório")
        String cpf,
        String phone,

        @NotBlank(message = "A data de nascimento do voluntário é obrigatória")
        LocalDate birthDate
) {
}
