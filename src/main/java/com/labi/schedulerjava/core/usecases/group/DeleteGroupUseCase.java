package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteGroupUseCase extends UseCase<DeleteGroupUseCase.InputValues, DeleteGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Group group = groupRepository.findById(input.groupId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.groupId + " não corresponde a nenhum grupo"));

        group.getVolunteers().forEach(volunteer -> {
            volunteer.setGroup(null);
        });

        groupRepository.delete(group);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long groupId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
