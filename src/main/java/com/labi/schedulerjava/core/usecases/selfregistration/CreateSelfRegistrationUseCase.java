package com.labi.schedulerjava.core.usecases.selfregistration;

import com.labi.schedulerjava.adapters.persistence.SelfRegistrationRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.SelfRegistration;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CreateSelfRegistrationUseCase extends UseCase<CreateSelfRegistrationUseCase.InputValues, CreateSelfRegistrationUseCase.OutputValues> {

    @Autowired
    private SelfRegistrationRepository selfRegistrationRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Override
    public OutputValues execute(InputValues input) {
        SelfRegistration selfRegistration = selfRegistrationRepository.findByLink(input.link)
                .orElseThrow(() -> new BusinessRuleException("Link não encontrado"));

        if (!selfRegistration.getIsActive())
            throw new BusinessRuleException("Você já está cadastrado");

        Volunteer volunteer = new Volunteer(
                input.dto.name(),
                input.dto.lastName(),
                input.dto.phone(),
                input.dto.birthDate()
        );

        volunteer.setSelfRegistration(selfRegistration);
        volunteerService.save(volunteer);

        selfRegistration.setIsActive(false);
        selfRegistration.setUsedAt(Instant.now());
        selfRegistrationRepository.save(selfRegistration);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        UUID link;
        CreateVolunteerDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
