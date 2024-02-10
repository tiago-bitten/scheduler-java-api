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

import java.util.UUID;

@Component
public class CreateUnavailableDateByAccessKeyUseCase extends UseCase<CreateUnavailableDateByAccessKeyUseCase.InputValues, CreateUnavailableDateByAccessKeyUseCase.OutputValues> {

    @Autowired
    private UnavailableDateRepository unavailableDateRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = volunteerService.findByAccessKey(UUID.fromString(input.getAccessKey()))
                .orElseThrow(() -> new BusinessRuleException("A chave de acesso informada " + input.getAccessKey() + " não corresponde a um voluntário cadastrado"));

        UnavailableDate unavailableDate = new UnavailableDate(input.dto.startDate(), input.dto.endDate(), input.dto.rrule(), volunteer);
        unavailableDateRepository.save(unavailableDate);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        CreateUnavailableDateDto dto;
        String accessKey;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
