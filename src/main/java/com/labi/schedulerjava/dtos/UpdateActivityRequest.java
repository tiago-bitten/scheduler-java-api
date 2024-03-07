package com.labi.schedulerjava.dtos;

public record UpdateActivityRequest(
        String name,
        Long defaultTotalVolunteers
) {
}
