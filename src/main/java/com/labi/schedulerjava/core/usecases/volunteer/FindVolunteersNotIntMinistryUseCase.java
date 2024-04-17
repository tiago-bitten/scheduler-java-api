package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.adapters.persistence.specification.VolunteerSpecification;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindVolunteersNotIntMinistryUseCase extends UseCase<FindVolunteersNotIntMinistryUseCase.InputValues, FindVolunteersNotIntMinistryUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private MinistryService ministryService;

    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.ministryId + " não corresponde a nenhum ministério"));

        Specification<Volunteer> spec = Specification.where(VolunteerSpecification.hasName(input.volunteerName));

        List<Volunteer> volunteers = volunteerRepository.findAll(spec);
        List<Volunteer> volunteersNotInMinistry = volunteers.stream()
                .filter(volunteer -> volunteer.getVolunteerMinistries().stream()
                .noneMatch(volunteerMinistry -> volunteerMinistry.getMinistry().getId().equals(ministry.getId()) && volunteerMinistry.getIsActive()))
                .toList();

        return new OutputValues(
                volunteersNotInMinistry.stream()
                        .map(volunteer -> new ReadSimpVolunteerDto(
                                volunteer.getId(),
                                volunteer.getAccessKey(),
                                volunteer.getName(),
                                volunteer.getLastName(),
                                volunteer.getCpf(),
                                volunteer.getPhone(),
                                volunteer.getBirthDate(),
                                volunteer.getOrigin()
                        ))
                        .toList()
        );
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
}
