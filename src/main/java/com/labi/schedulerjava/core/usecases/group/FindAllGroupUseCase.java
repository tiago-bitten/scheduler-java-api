package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadGroupDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllGroupUseCase extends UseCase<FindAllGroupUseCase.InputValues, FindAllGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public OutputValues execute(InputValues input) {
        List<ReadGroupDto> groups = groupRepository.findAll().stream()
                .map(group -> new ReadGroupDto(
                        group.getId(),
                        group.getName(),
                        group.getVolunteers().stream()
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
        ))
        .toList();

        return new OutputValues(groups);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadGroupDto> groups;
    }
}
