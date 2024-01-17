package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateMinistryUseCase extends UseCase<CreateMinistryUseCase.InputValues, CreateMinistryUseCase.OutputValues> {

    @Autowired
    private MinistryRepository ministryRepository;

    @Override
    public OutputValues execute(InputValues input) {
        if (ministryRepository.findByName(input.dto.name()).isPresent()) {
            throw new BusinessRuleException("Este ministério já está cadastrado");
        }

        Ministry ministry = new Ministry(input.dto.name(), input.dto.description(), input.dto.color());
        ministryRepository.save(ministry);
        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private CreateMinistryDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
