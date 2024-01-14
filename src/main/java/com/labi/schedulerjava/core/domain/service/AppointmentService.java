package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.core.domain.model.Schedule;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    public Boolean validateAppointment(Schedule schedule, Long volunteerId) {
        return schedule.getAppointments().stream()
                .anyMatch(appointment -> appointment.getVolunteerMinistry().getVolunteer().getId().equals(volunteerId));
    }
}
