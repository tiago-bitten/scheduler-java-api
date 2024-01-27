package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.*;

import java.util.List;

public record SignUpDto(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password,

        List<Long> ministries
) {
}
