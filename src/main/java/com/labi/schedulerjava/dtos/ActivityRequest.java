package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActivityRequest(
        @NotBlank(message = "O campo nome é obrigatório")
        String name,

        @NotNull(message = "O campo do número total de voluntários é obrigatório")
        Long totalVolunteers
) {
}
