package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAppointmentUseCase extends UseCase<CreateAppointmentUseCase.InputValues, CreateAppointmentUseCase.OutputValues> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ActivityService activityService;


    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        if (!userMinistryService.existsUserMinistryRelation(user.getId(), input.ministryId))
            throw new BusinessRuleException("Você não tem permissão para agendar voluntários neste ministério");

        Schedule schedule = scheduleService.validateSchedule(input.getScheduleId());
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.validateVolunteerMinistry(input.volunteerId, input.ministryId);
        Activity activity = activityService.findById(input.activityId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.activityId + " não corresponde a nenhuma atividade cadastrada"));

        if (appointmentService.validateAppointment(schedule, input.volunteerId))
            throw new BusinessRuleException("Voluntário já agendado neste horário");

        if (unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), input.volunteerId))
            throw new BusinessRuleException("Voluntário não pode ser agendado nesta data");

        Appointment appointment = new Appointment(schedule, volunteerMinistry, activity, user);
        appointmentRepository.save(appointment);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
        Long volunteerId;
        Long ministryId;
        Long activityId;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}