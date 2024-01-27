package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpMinistryDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindMinistriesToSignUpUseCase extends UseCase<FindMinistriesToSignUpUseCase.InputValues, FindMinistriesToSignUpUseCase.OutputValues> {

    @Autowired
    private MinistryRepository ministryRepository;

    @Override
    public OutputValues execute(InputValues input) {
        List<Ministry> ministries = ministryRepository.findAll();

        return new OutputValues(
                ministries.stream()
                        .map((ministry) -> new ReadSimpMinistryDto(ministry.getId(), ministry.getName()))
                        .toList()
        );
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadSimpMinistryDto> ministries;
    }
}
