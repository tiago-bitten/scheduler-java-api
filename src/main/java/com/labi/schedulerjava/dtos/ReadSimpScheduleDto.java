package com.labi.schedulerjava.dtos;

import java.time.LocalDateTime;

public record ReadSimpScheduleDto(
        Long id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean isActive
) { }
