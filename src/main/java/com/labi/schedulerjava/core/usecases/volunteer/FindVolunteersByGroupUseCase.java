package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.service.GroupService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Component
public class FindVolunteersByGroupUseCase extends UseCase<FindVolunteersByGroupUseCase.InputValues, FindVolunteersByGroupUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private GroupService groupService;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupService.findById(input.groupId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.groupId + " não corresponde a nenhum grupo"));

        return new OutputValues(
                volunteerRepository.findAllByGroup(group.getId()).stream()
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

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long groupId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadVolunteerDto> volunteers;
    }
}
