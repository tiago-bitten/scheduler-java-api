package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.domain.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Boolean validateAppointment(Schedule schedule, Long volunteerId) {
        return schedule.getAppointments().stream()
                .anyMatch(appointment -> appointment.getVolunteerMinistry().getVolunteer().getId().equals(volunteerId));
    }

    public List<Appointment> findAll(Specification<Appointment> spec) {
        return appointmentRepository.findAll(spec);
    }
}
