package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
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
public class AutoCreateVolunteerUseCase extends UseCase<AutoCreateVolunteerUseCase.InputValues, AutoCreateVolunteerUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerLogService volunteerLogService;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = new Volunteer(input.dto.name(), input.dto.lastName(), input.dto.cpf(), input.dto.phone(), input.dto.birthDate(), VolunteerOrigin.SELF_REGISTERED);
        volunteerRepository.save(volunteer);

        volunteerLogService.log(volunteer, Actions.CREATE, ActionsOrigin.BY_VOLUNTEER);

        return new OutputValues(new ReadSimpVolunteerDto(volunteer.getId(), volunteer.getAccessKey(), volunteer.getName(), volunteer.getLastName(), volunteer.getCpf(), volunteer.getPhone(), volunteer.getBirthDate(), volunteer.getOrigin()));
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
