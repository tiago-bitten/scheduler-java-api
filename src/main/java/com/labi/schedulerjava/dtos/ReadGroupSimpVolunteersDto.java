package com.labi.schedulerjava.dtos;

import java.util.List;

public record ReadGroupSimpVolunteersDto(
        Long id,
        String name,
        List<ReadSimpVolunteerDto> volunteers
) {
}
