package com.labi.schedulerjava.dtos;

public record ReadAppointmentsDto(
        Long id,
        ReadVolunteerMinistry volunteerMinistry,
        ActivityResponse activity
) {
}
