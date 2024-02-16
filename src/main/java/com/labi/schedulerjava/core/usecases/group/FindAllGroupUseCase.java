package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.adapters.persistence.specification.GroupSpecification;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadGroupDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllGroupUseCase extends UseCase<FindAllGroupUseCase.InputValues, FindAllGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Specification<Group> spec = Specification.where(null);

        if (input.groupName != null)
            spec = spec.and(GroupSpecification.hasName(input.groupName));

        if (input.volunteerName != null)
            spec = spec.and(GroupSpecification.hasVolunteer(input.volunteerName));

        List<Group> groups = groupRepository.findAll(spec);

        return new OutputValues(groups.stream().map(this::toDto).toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String groupName;
        String volunteerName;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadGroupDto> groups;
    }

    private ReadGroupDto toDto(Group entity) {
        return new ReadGroupDto(
                entity.getId(),
                entity.getName(),
                entity.getVolunteers().stream()
                        .map(volunteer -> new ReadVolunteerDto(
                                volunteer.getId(),
                                volunteer.getAccessKey(),
                                volunteer.getName(),
                                volunteer.getLastName(),
                                volunteer.getPhone(),
                                volunteer.getBirthDate(),
                                volunteer.getVolunteerMinistries().stream()
                                        .map(volunteerMinistry -> new ReadMinistryDto(
                                                volunteerMinistry.getMinistry().getId(),
                                                volunteerMinistry.getMinistry().getName(),
                                                volunteerMinistry.getMinistry().getDescription(),
                                                volunteerMinistry.getMinistry().getColor(),
                                                volunteerMinistry.getMinistry().getTotalVolunteers()
                                        ))
                                        .toList()
                        ))
                        .toList()
        );
    }
}
