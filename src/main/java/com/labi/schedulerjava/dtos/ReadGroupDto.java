package com.labi.schedulerjava.dtos;

import java.util.List;

public record ReadGroupDto(
        Long id,
        String name,
        List<ReadVolunteerDto> volunteers
) {
}
