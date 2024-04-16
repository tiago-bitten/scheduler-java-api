package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.adapters.persistence.specification.GroupSpecification;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.*;
import lombok.Value;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.ministryId + " não corresponde a um ministério cadastrado"));

        Schedule schedule = scheduleService.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.scheduleId + " não corresponde a uma escala cadastrada"));

        List<Group> groups = groupRepository.findAll(GroupSpecification.hasMinistryId(input.ministryId));
        List<ReadVolunteersConditionsDto> conditionsDtos = new ArrayList<>();

        for (Group group : groups) {
            for (Volunteer volunteer : group.getVolunteers()) {
                Optional<VolunteerMinistry> optionalVolunteerMinistry = volunteerMinistryService.findByVolunteerAndMinistry(volunteer, ministry);
                if (optionalVolunteerMinistry.isEmpty() || !optionalVolunteerMinistry.get().getIsActive()) {
                    addVolunteer(volunteer, conditionsDtos, false, "Voluntário não está vinculado ao ministério " + ministry.getName());
                    continue;
                }

                VolunteerMinistry volunteerMinistry = optionalVolunteerMinistry.get();

                if (appointmentService.validateAppointment(schedule, volunteer.getId())) {
                    addVolunteer(volunteer, conditionsDtos, false, "Voluntário já está agendado nesta agenda");
                    continue;
                }

                if (unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId())) {
                    addVolunteer(volunteer, conditionsDtos, false, "Voluntário está indisponível nesta data");
                    continue;
                }

                addVolunteer(volunteer, conditionsDtos, true, null);
            }
        }


        List<ReadGroupToAppointDto> groupsDtos = groups.stream()
                .map(group -> toDto(group, conditionsDtos))
                .toList();

        return new OutputValues(groupsDtos);
    }


    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
        Long scheduleId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadGroupToAppointDto> groups;
    }

    private ReadGroupToAppointDto toDto(Group group, List<ReadVolunteersConditionsDto> conditionsDtos) {
        return new ReadGroupToAppointDto(
                group.getId(),
                group.getName(),
                conditionsDtos
        );
    }

    private void addVolunteer(Volunteer volunteer, List<ReadVolunteersConditionsDto> conditions, Boolean available, String reason) {
        conditions.add(new ReadVolunteersConditionsDto(
                volunteer.getId(),
                volunteer.getName(),
                volunteer.getLastName(),
                available,
                reason
        ));
    }
}
