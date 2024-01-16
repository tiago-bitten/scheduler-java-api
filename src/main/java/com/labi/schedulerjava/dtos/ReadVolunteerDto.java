package com.labi.schedulerjava.dtos;

import java.time.LocalDate;
import java.util.List;

public record ReadVolunteerDto(
        Long id,
        String name,
        String lastName,
        String phone,
        LocalDate birthDate,
        List<ReadMinistryDto> ministries
) {
}
