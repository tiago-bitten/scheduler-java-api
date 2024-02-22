package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateMinistryUseCase extends UseCase<CreateMinistryUseCase.InputValues, CreateMinistryUseCase.OutputValues> {

    @Autowired
    private MinistryRepository ministryRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserMinistryService userMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        if (ministryRepository.findByName(input.dto.name()).isPresent()) {
            throw new BusinessRuleException("Este ministério já está cadastrado");
        }

        Ministry ministry = new Ministry(input.dto.name().toUpperCase(), input.dto.description(), input.dto.color());
        ministryRepository.save(ministry);

        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        userMinistryService.associate(user, List.of(ministry.getId()));

        return new OutputValues(toDto(ministry));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        CreateMinistryDto dto;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadMinistryDto ministry;
    }

    private ReadMinistryDto toDto(Ministry ministry) {
        return new ReadMinistryDto(ministry.getId(), ministry.getName(), ministry.getDescription(), ministry.getColor(), ministry.getTotalVolunteers());
    }
}
