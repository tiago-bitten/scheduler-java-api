package com.labi.schedulerjava.core.usecases.volunteerministry;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindVolunteerMinistryByMinistryIdUseCase extends UseCase<FindVolunteerMinistryByMinistryIdUseCase.InputValues, FindVolunteerMinistryByMinistryIdUseCase.OutputValues> {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    @Override
    public OutputValues execute(InputValues input) {
        List<VolunteerMinistry> volunteerMinistries = volunteerMinistryRepository.findByMinistryId(input.ministryId);
        return new OutputValues(
                volunteerMinistries.stream()
                        .map(volunteerMinistry -> new ReadSimpVolunteerDto(
                                volunteerMinistry.getVolunteer().getId(),
                                volunteerMinistry.getVolunteer().getName(),
                                volunteerMinistry.getVolunteer().getLastName(),
                                volunteerMinistry.getVolunteer().getPhone(),
                                volunteerMinistry.getVolunteer().getBirthDate()
                        ))
                        .toList()
        );
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadSimpVolunteerDto> volunteers;
    }
}
