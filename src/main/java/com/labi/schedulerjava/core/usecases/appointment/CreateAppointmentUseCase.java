package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.ScheduleService;
import com.labi.schedulerjava.core.domain.service.UserService;
import com.labi.schedulerjava.core.domain.service.VolunteerMinistryService;
import com.labi.schedulerjava.enterprise.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    public void create(Long scheduleId, Long volunteerId, Long ministryId, Long userId) {
        User user = userService.findById(userId).get();
        Schedule schedule = scheduleService.validateSchedule(scheduleId);
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.validateVolunteerMinistry(volunteerId, ministryId);

        Optional<Appointment> existsAppointment = schedule.getAppointments().stream()
                .filter(appointment -> appointment.getVolunteerMinistry().getVolunteer().getId().equals(volunteerId))
                .findFirst();

        if (existsAppointment.isPresent())
            throw new BusinessRuleException("Voluntário já está vinculado a esta agenda");

        Appointment appointment = new Appointment(schedule, volunteerMinistry, user);
        appointmentRepository.save(appointment);
    }
}