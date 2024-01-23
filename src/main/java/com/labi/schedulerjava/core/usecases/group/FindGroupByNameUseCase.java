package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadGroupDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FindGroupByNameUseCase extends UseCase<FindGroupByNameUseCase.InputValues, FindGroupByNameUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupRepository.findByName(input.name)
                .orElseThrow(() -> new BusinessRuleException("Não existe um grupo com este nome"));

        return new OutputValues(
                new ReadGroupDto(
                        group.getId(),
                        group.getName(),
                        group.getVolunteers().stream()
                                .map(this::toDto)
                                .toList()
                ));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String name;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadGroupDto group;
    }

    private ReadVolunteerDto toDto(Volunteer entity) {
        return new ReadVolunteerDto(
                entity.getId(),
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
