package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.AssociateVolunteerWithGroupDto;
import lombok.Value;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AssociateVolunteerWithGroupUseCase extends UseCase<AssociateVolunteerWithGroupUseCase.InputValues, AssociateVolunteerWithGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupRepository.findById(input.groupId)
                .orElseThrow(() -> new BusinessRuleException("Grupo não encontrado"));

        for (Long volunteerId : input.dto.volunteersId()) {
            Optional<Volunteer> existsVolunteer = volunteerService.findById(volunteerId);
            existsVolunteer.ifPresent(group::addVolunteer);
        }

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long groupId;
        AssociateVolunteerWithGroupDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
