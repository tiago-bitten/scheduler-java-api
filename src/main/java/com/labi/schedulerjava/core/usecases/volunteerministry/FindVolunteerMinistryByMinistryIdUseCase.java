package com.labi.schedulerjava.core.usecases.volunteerministry;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.adapters.persistence.specification.VolunteerMinistrySpecification;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindVolunteerMinistryByMinistryIdUseCase extends UseCase<FindVolunteerMinistryByMinistryIdUseCase.InputValues, FindVolunteerMinistryByMinistryIdUseCase.OutputValues> {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryService.findById(input.getMinistryId())
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.getMinistryId() + " não corresponde a um ministério cadastrado"));

        Specification<VolunteerMinistry> spec = Specification.where(null);

        spec = spec.and(VolunteerMinistrySpecification.hasMinistryId(ministry.getId()));

        if (input.getVolunteerName() != null) {
            spec = spec.and(VolunteerMinistrySpecification.hasVolunteerName(input.getVolunteerName()));
        }

        List<VolunteerMinistry> volunteerMinistries = volunteerMinistryRepository.findAll(spec);

        return new OutputValues(volunteerMinistries.stream()
                .map(this::toDto)
                .toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
        String volunteerName;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadSimpVolunteerDto> volunteers;
    }

    private ReadSimpVolunteerDto toDto(VolunteerMinistry volunteerMinistry) {
        return new ReadSimpVolunteerDto(
                volunteerMinistry.getVolunteer().getId(),
                volunteerMinistry.getVolunteer().getAccessKey(),
                volunteerMinistry.getVolunteer().getName(),
                volunteerMinistry.getVolunteer().getLastName(),
                volunteerMinistry.getVolunteer().getCpf(),
                volunteerMinistry.getVolunteer().getPhone(),
                volunteerMinistry.getVolunteer().getBirthDate(),
                volunteerMinistry.getVolunteer().getOrigin()
        );
    }
}
