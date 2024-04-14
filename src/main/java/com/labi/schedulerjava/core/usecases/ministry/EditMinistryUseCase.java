package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.EditMinistryDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditMinistryUseCase extends UseCase<EditMinistryUseCase.InputValues, EditMinistryUseCase.OutputValues> {

    @Autowired
    private MinistryRepository ministryRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserMinistryService userMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryRepository.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.ministryId + " não corresponde a nenhum ministério cadastrado"));

        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        if (!userMinistryService.existsUserMinistryRelation(user.getId(), ministry.getId()))
            throw new BusinessRuleException("Você não tem permissão para editar este ministério");

        if (input.dto.name() != null)
            ministry.setName(input.dto.name().toUpperCase());

        if (input.dto.description() != null)
            ministry.setDescription(input.dto.description());

        if (input.dto.color() != null)
            ministry.setColor(input.dto.color());

        ministryRepository.save(ministry);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        EditMinistryDto dto;
        Long ministryId;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
