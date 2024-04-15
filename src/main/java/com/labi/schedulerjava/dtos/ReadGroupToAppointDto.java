package com.labi.schedulerjava.dtos;

import java.util.List;

public record ReadGroupToAppointDto(
        Long id,
        String name,
        List<ReadVolunteersConditionsDto> volunteers
) {
}