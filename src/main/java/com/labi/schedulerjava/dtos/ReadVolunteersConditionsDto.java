package com.labi.schedulerjava.dtos;

public record ReadVolunteersConditionsDto(
        Long id,
        String name,
        String lastName,
        Boolean available,
        String reason
) {}
