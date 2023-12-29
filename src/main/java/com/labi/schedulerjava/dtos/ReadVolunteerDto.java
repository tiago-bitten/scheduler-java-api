package com.labi.schedulerjava.dtos;

import java.util.List;

public record ReadVolunteerDto(
        Long id,
        String name,
        String lastName,
        String phone,
        String birthDate,
        List<ReadMinistryDto> ministries
) {
}
