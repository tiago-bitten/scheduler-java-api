package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisassociateVolunteerGroupUseCase extends UseCase<DisassociateVolunteerGroupUseCase.InputValues, DisassociateVolunteerGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupRepository.findById(input.groupId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.groupId + " não corresponde a nenhum grupo"));

        Volunteer volunteer = volunteerService.findById(input.volunteerId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.volunteerId + " não corresponde a nenhum voluntário"));

        if (!group.getVolunteers().contains(volunteer))
            throw new BusinessRuleException("O voluntário não está associado a este grupo");

        group.removeVolunteer(volunteer);
        groupRepository.save(group);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long groupId;
        Long volunteerId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
