package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.exception.SignInVolunteerException;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.VolunteerLogService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import com.labi.schedulerjava.dtos.SignInVolunteerDto;
import com.labi.schedulerjava.enums.Actions;
import com.labi.schedulerjava.enums.ActionsOrigin;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SignInVolunteerUseCase extends UseCase<SignInVolunteerUseCase.InputValues, SignInVolunteerUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerLogService volunteerLogService;

    @Override
    public OutputValues execute(InputValues input) {
        Optional<Volunteer> volunteer = volunteerRepository.signInVolunteer(input.dto.cpf(), input.dto.birthDate());

        if (volunteer.isPresent()) {
            volunteerLogService.log(volunteer.get(), Actions.SIGN_IN, ActionsOrigin.BY_VOLUNTEER);
            return new OutputValues(new ReadSimpVolunteerDto(volunteer.get().getId(), volunteer.get().getAccessKey(), volunteer.get().getName(), volunteer.get().getLastName(), volunteer.get().getCpf(), volunteer.get().getPhone(), volunteer.get().getBirthDate(), volunteer.get().getOrigin()));
        }

        volunteerRepository.findByCpf(input.dto.cpf()).ifPresent(v -> {
            volunteerLogService.log(v, Actions.INVALID_BIRTHDATE, ActionsOrigin.BY_VOLUNTEER);
            throw new SignInVolunteerException();
        });

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
