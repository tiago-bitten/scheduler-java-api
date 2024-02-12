package com.labi.schedulerjava.core.usecases.group;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateGroupDto;
import com.labi.schedulerjava.dtos.ReadSimpGroupDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateGroupUseCase extends UseCase<CreateGroupUseCase.InputValues, CreateGroupUseCase.OutputValues> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public OutputValues execute(InputValues input) {
        if (groupRepository.findByName(input.dto.name()).isPresent())
            throw new BusinessRuleException("Já existe um grupo com este nome");

        Group group = new Group(input.dto.name());
        groupRepository.save(group);

        return new OutputValues(new ReadSimpGroupDto(group.getId(), group.getName()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        CreateGroupDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadSimpGroupDto group;
    }
}
