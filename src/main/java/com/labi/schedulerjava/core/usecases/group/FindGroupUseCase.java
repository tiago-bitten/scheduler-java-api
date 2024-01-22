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

import java.util.List;

@Component
public class FindGroupUseCase extends UseCase<FindGroupUseCase.InputValues, FindGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupRepository.findById(input.groupId)
                .orElseThrow(() -> new BusinessRuleException("Grupo não encontrado"));

        List<Volunteer> volunteers = group.getVolunteers();

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
        Long groupId;
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
