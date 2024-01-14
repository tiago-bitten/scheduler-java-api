package com.labi.schedulerjava.dtos;

import java.time.Instant;
import java.util.List;

public record ReadUserDto(
        Long id,
        String name,
        String email,
        Boolean isApproved,
        Instant createdAt,
        List<ReadMinistryDto> ministries
) {
}
