package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.NotBlank;

public record ActivityRequest(
        @NotBlank(message = "O campo nome é obrigatório")
        String name,

        @NotBlank(message = "O campo do número total de voluntários é obrigatório")
        Long totalVolunteers
) {
}
