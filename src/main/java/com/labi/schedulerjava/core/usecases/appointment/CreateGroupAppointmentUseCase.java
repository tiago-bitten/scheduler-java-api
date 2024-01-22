package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CreateGroupAppointmentUseCase extends UseCase<CreateGroupAppointmentUseCase.InputValues, CreateGroupAppointmentUseCase.OutputValues> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        if (!userMinistryService.existsUserMinistryRelation(user.getId(), input.ministryId))
            throw new BusinessRuleException("Você não tem permissão para agendar esse grupo neste ministério");

        Schedule schedule = scheduleService.validateSchedule(input.getScheduleId());
        Group group = groupService.findById(input.groupId)
                .orElseThrow(() -> new BusinessRuleException("Grupo não encontrado"));

        Set<VolunteerMinistry> volunteerMinistries = new HashSet<>();
        group.getVolunteers().forEach(volunteer -> {
            volunteerMinistries.add(volunteerMinistryService.validateVolunteerMinistry(volunteer.getId(), input.ministryId));

            if (appointmentService.validateAppointment(schedule, volunteer.getId()))
                throw new BusinessRuleException("Voluntário " + volunteer.getName() + " já agendado neste horário");

            if (unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId()))
                throw new BusinessRuleException("Voluntário " + volunteer.getName() + " não pode ser agendado nesta data");
        });

        volunteerMinistries.forEach(volunteerMinistry -> {
            Appointment appointment = new Appointment(schedule, volunteerMinistry, user);
            appointmentRepository.save(appointment);
        });

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long groupId;
        Long ministryId;
        Long scheduleId;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
