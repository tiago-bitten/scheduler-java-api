package com.labi.schedulerjava.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReadVolunteerDto(
        Long id,
        UUID accessKey,
        String name,
        String lastName,
        String phone,
        LocalDate birthDate,
        List<ReadMinistryDto> ministries
) {
}
