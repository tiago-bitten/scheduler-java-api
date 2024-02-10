package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import com.labi.schedulerjava.dtos.SignInVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SignInVolunteerUseCase extends UseCase<SignInVolunteerUseCase.InputValues, SignInVolunteerUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Optional<Volunteer> volunteer = volunteerRepository.signInVolunteer(input.dto.cpf(), input.dto.birthDate());

        if (volunteer.isPresent()) {
            return new OutputValues(new ReadSimpVolunteerDto(volunteer.get().getId(), volunteer.get().getAccessKey(), volunteer.get().getName(), volunteer.get().getLastName(), volunteer.get().getCpf(), volunteer.get().getPhone(), volunteer.get().getBirthDate(), volunteer.get().getOrigin()));
        }
        throw new BusinessRuleException("Force to create a volunteer");
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        SignInVolunteerDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadSimpVolunteerDto volunteer;
    }
}
