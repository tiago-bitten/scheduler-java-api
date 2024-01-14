package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.AppointmentService;
import com.labi.schedulerjava.core.domain.service.ScheduleService;
import com.labi.schedulerjava.core.domain.service.UserService;
import com.labi.schedulerjava.core.domain.service.VolunteerMinistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private AppointmentService appointmentService;

    public void create(Long scheduleId, Long volunteerId, Long ministryId, Long userId) {
        User user = userService.findById(userId).get();
        Schedule schedule = scheduleService.validateSchedule(scheduleId);
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.validateVolunteerMinistry(volunteerId, ministryId);

        if (appointmentService.validateAppointment(schedule, volunteerId))
            throw new BusinessRuleException("Voluntário já agendado neste horário");

        Appointment appointment = new Appointment(schedule, volunteerMinistry, user);
        appointmentRepository.save(appointment);
    }
}