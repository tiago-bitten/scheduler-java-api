package com.labi.schedulerjava.dtos;

import java.time.LocalDateTime;

public record ReadSimpSchedule(
        Long id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer weekNumber,
        Boolean isActive
) { }
