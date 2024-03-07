package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateActivityRequest(

        @NotBlank(message = "Nome não pode ser em branco")
        String name,

        @NotNull(message = "Total de voluntários não pode ser nulo")
        Long defaultTotalVolunteers
) {
}
