package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateGroupAppointmentDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateGroupAppointmentUseCase extends UseCase<CreateGroupAppointmentUseCase.InputValues, CreateGroupAppointmentUseCase.OutputValues> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.getAuthHeader());

        Schedule schedule = scheduleService.validateSchedule(input.getScheduleId());

        Ministry ministry = ministryService.findById(input.getMinistryId())
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.getMinistryId() + " não corresponde a um ministério cadastrado"));

        if (!userMinistryService.existsUserMinistryRelation(user.getId(), input.getMinistryId()))
            throw new BusinessRuleException("Você não tem permissão para agendar voluntários neste ministério");

        input.getAppointments().forEach((appointment) -> {
            Volunteer volunteer = volunteerService.findById(appointment.volunteerId())
                    .orElseThrow(() -> new BusinessRuleException("O ID " + appointment.volunteerId() + " não corresponde a nenhum voluntário cadastrado"));
            Activity activity = activityService.findById(appointment.activityId())
                    .orElseThrow(() -> new BusinessRuleException("O ID " + appointment.activityId() + " não corresponde a nenhuma atividade cadastrada"));

            if (appointmentService.validateAppointment(schedule, volunteer.getId()))
                throw new BusinessRuleException("Voluntário já agendado neste horário");

            if (unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId()))
                throw new BusinessRuleException("Voluntário não pode ser agendado nesta data");

            volunteerMinistryService.validateVolunteerMinistry(volunteer.getId(), input.getMinistryId());
        });

        input.getAppointments().forEach((appointment) -> {
            Volunteer volunteer = volunteerService.findById(appointment.volunteerId()).get();
            Activity activity = activityService.findById(appointment.activityId()).get();

            VolunteerMinistry volunteerMinistry = volunteerMinistryService.findByVolunteerAndMinistry(volunteer, ministry).get();

            Appointment instanceAppointment = new Appointment(schedule, volunteerMinistry, activity, user);
            appointmentRepository.save(instanceAppointment);
        });

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        Long scheduleId;
        Long ministryId;
        List<CreateGroupAppointmentDto> appointments;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}

