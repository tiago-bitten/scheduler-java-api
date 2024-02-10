package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadGroupDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class FindGroupByNameToAppointUseCase extends UseCase<FindGroupByNameToAppointUseCase.InputValues, FindGroupByNameToAppointUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupRepository.findByName(input.name)
                .orElseThrow(() -> new BusinessRuleException("Não existe um grupo com este nome"));

        Schedule schedule = scheduleService.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Horário não encontrado"));

        Set<Volunteer> volunteers = new HashSet<>();
        group.getVolunteers().forEach(volunteer -> {
            if (volunteerMinistryService.findByVolunteerAndMinistry(volunteer.getId(), input.ministryId).isEmpty())
                return;

            if (appointmentService.validateAppointment(schedule, volunteer.getId()))
                return;

            if (unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId()))
                return;

            volunteers.add(volunteer);
        });


        return new OutputValues(
                new ReadGroupDto(
                        group.getId(),
                        group.getName(),
                        volunteers.stream()
                                .map(this::toDto)
                                .toList()
                ));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String name;
        Long ministryId;
        Long scheduleId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadGroupDto group;
    }

    private ReadVolunteerDto toDto(Volunteer entity) {
        return new ReadVolunteerDto(
                entity.getId(),
                entity.getAccessKey(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getBirthDate(),
                entity.getVolunteerMinistries().stream()
                        .map(volunteerMinistry -> new ReadMinistryDto(
                                volunteerMinistry.getMinistry().getId(),
                                volunteerMinistry.getMinistry().getName(),
                                volunteerMinistry.getMinistry().getDescription(),
                                volunteerMinistry.getMinistry().getColor(),
                                volunteerMinistry.getMinistry().getTotalVolunteers()
                        )).toList()
        );
    }
}
