package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.adapters.persistence.specification.GroupSpecification;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadGroupDto;
import com.labi.schedulerjava.dtos.ReadGroupSimpVolunteersDto;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindGroupsNotInScheduleUseCase extends UseCase<FindGroupsNotInScheduleUseCase.InputValues, FindGroupsNotInScheduleUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        Schedule schedule = scheduleService.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.scheduleId + " não corresponde a um horário cadastrado"));

        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.ministryId + " não corresponde a um ministério cadastrado"));

        List<Group> groups = groupRepository.findAll(GroupSpecification.hasMinistryId(input.ministryId));

        List<Group> filteredGroups = groups.stream()
                .filter(group -> group.getVolunteers().stream()
                        .anyMatch(volunteer ->
                        volunteer.getVolunteerMinistries().stream()
                                .anyMatch(volunteerMinistry ->
                                volunteerMinistry.getIsActive() &&
                                        volunteerMinistry.getAppointments().stream()
                                                .noneMatch(appointment -> appointment.getSchedule().equals(schedule) &&
                                                        !unavailableDateService.isUnavailableDate(
                                                                appointment.getSchedule().getStartDate(),
                                                                appointment.getSchedule().getEndDate(),
                                                                volunteer.getId()
                                                        )
                                                )
                                )
                        )
                )
                .toList();

        List<ReadGroupSimpVolunteersDto> groupDtos = filteredGroups.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new OutputValues(groupDtos);
    }


    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadGroupSimpVolunteersDto> groups;
    }

    private ReadGroupSimpVolunteersDto toDto(Group group) {
        return new ReadGroupSimpVolunteersDto(
                group.getId(),
                group.getName(),
                group.getVolunteers().stream().map(volunteer -> new ReadSimpVolunteerDto(
                        volunteer.getId(),
                        volunteer.getAccessKey(),
                        volunteer.getName(),
                        volunteer.getLastName(),
                        volunteer.getCpf(),
                        volunteer.getPhone(),
                        volunteer.getBirthDate(),
                        volunteer.getOrigin()
                )).collect(Collectors.toList())
        );
    }
}
