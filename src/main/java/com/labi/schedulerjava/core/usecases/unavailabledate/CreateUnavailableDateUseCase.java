package com.labi.schedulerjava.core.usecases.unavailabledate;

import com.labi.schedulerjava.adapters.persistence.UnavailableDateRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.UnavailableDate;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateUnavailableDateDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUnavailableDateUseCase extends UseCase<CreateUnavailableDateUseCase.InputValues, CreateUnavailableDateUseCase.OutputValues> {

    @Autowired
    private UnavailableDateRepository unavailableDateRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = volunteerService.findById(input.getVolunteerId())
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.getVolunteerId() + " não corresponde a um voluntário cadastrado"));

        UnavailableDate unavailableDate = new UnavailableDate(input.dto.date(), input.dto.rrule(), volunteer);
        unavailableDateRepository.save(unavailableDate);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        CreateUnavailableDateDto dto;
        Long volunteerId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
