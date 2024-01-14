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
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAppointmentUseCase extends UseCase<CreateAppointmentUseCase.InputValues, CreateAppointmentUseCase.OutputValues> {

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

    @Override
    public OutputValues execute(InputValues input) {
        User user = userService.findById(input.getUserId()).get();
        Schedule schedule = scheduleService.validateSchedule(input.getScheduleId());
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.validateVolunteerMinistry(input.volunteerId, input.ministryId);

        if (appointmentService.validateAppointment(schedule, input.volunteerId))
            throw new BusinessRuleException("Voluntário já agendado neste horário");

        Appointment appointment = new Appointment(schedule, volunteerMinistry, user);
        appointmentRepository.save(appointment);
        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private Long scheduleId;
        private Long volunteerId;
        private Long ministryId;
        private Long userId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}