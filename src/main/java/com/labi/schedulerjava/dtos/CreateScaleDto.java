package com.labi.schedulerjava.dtos;

import java.util.Map;

public record CreateScaleDto(
        Map<Long, Long> activityIdVolunteers
) {
}
