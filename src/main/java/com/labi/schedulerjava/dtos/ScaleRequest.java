package com.labi.schedulerjava.dtos;

import java.util.Map;

public record ScaleRequest(
        Map<Long, Long> activityIdVolunteers
) {
}
