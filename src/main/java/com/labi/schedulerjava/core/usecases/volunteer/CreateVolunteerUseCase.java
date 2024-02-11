package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.VolunteerLogService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import com.labi.schedulerjava.enums.Actions;
import com.labi.schedulerjava.enums.ActionsOrigin;
import com.labi.schedulerjava.enums.VolunteerOrigin;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateVolunteerUseCase extends UseCase<CreateVolunteerUseCase.InputValues, CreateVolunteerUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerLogService volunteerLogService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = new Volunteer(input.dto.name(), input.dto.lastName(), input.dto.cpf(), input.dto.phone(), input.dto.birthDate(), VolunteerOrigin.USER_REGISTERED);
        volunteerRepository.save(volunteer);

        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        volunteerLogService.log(volunteer, user, Actions.CREATE, ActionsOrigin.BY_USER);

        return new OutputValues(new ReadSimpVolunteerDto(volunteer.getId(), volunteer.getAccessKey(), volunteer.getName(), volunteer.getLastName(), volunteer.getCpf(), volunteer.getPhone(), volunteer.getBirthDate(), volunteer.getOrigin()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        CreateVolunteerDto dto;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadSimpVolunteerDto volunteer;
    }
}
