package com.labi.schedulerjava.dtos;

import java.util.List;
import java.util.Map;

public record CreateGroupAppointmentDto(
        Long volunteerId,
        Long activityId
) {
}
