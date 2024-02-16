package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.adapters.persistence.specification.MinistrySpecification;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindMinistriesUseCase extends UseCase<FindMinistriesUseCase.InputValues, FindMinistriesUseCase.OutputValues> {

    @Autowired
    private MinistryRepository ministryRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Specification<Ministry> spec = Specification.where(null);

        if (input.ministryName != null)
            spec = spec.and(MinistrySpecification.hasMinistry(input.ministryName));

        if (input.volunteerName != null)
            spec = spec.and(MinistrySpecification.hasVolunteer(input.volunteerName));

        List<Ministry> ministries = ministryRepository.findAll(spec);

        return new OutputValues(ministries.stream().map(this::toDto).toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String ministryName;
        String volunteerName;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadMinistryDto> ministries;
    }

    private ReadMinistryDto toDto(Ministry entity) {
        return new ReadMinistryDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getColor(),
                entity.getTotalVolunteers());
    }
}
