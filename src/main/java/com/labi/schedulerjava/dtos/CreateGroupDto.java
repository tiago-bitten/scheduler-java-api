package com.labi.schedulerjava.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateGroupDto(

        @NotBlank(message = "O nome do grupo é obrigatório")
        String name
) {
}
