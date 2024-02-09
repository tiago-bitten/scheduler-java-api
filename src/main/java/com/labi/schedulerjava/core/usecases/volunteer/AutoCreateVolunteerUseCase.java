package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import com.labi.schedulerjava.enums.VolunteerOrigin;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoCreateVolunteerUseCase extends UseCase<AutoCreateVolunteerUseCase.InputValues, AutoCreateVolunteerUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = new Volunteer(input.dto.name(), input.dto.lastName(), input.dto.phone(), input.dto.birthDate(), VolunteerOrigin.SELF_REGISTERED);
        volunteerRepository.save(volunteer);

        return new OutputValues(new ReadSimpVolunteerDto(volunteer.getId(), volunteer.getName(), volunteer.getLastName(), volunteer.getPhone(), volunteer.getBirthDate()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        CreateVolunteerDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadSimpVolunteerDto volunteer;
    }
}
