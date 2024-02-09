package com.labi.schedulerjava.core.usecases.selfregistration;

import com.labi.schedulerjava.adapters.persistence.SelfRegistrationRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.SelfRegistration;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValidateLinkUseCase extends UseCase<ValidateLinkUseCase.InputValues, ValidateLinkUseCase.OutputValues> {

    @Autowired
    private SelfRegistrationRepository selfRegistrationRepository;

    @Override
    public OutputValues execute(InputValues input) {
        SelfRegistration selfRegistration = selfRegistrationRepository.findByLink(input.link)
                .orElseThrow(() -> new BusinessRuleException("Link não encontrado"));

        if (!selfRegistration.getIsActive()) {
            throw new BusinessRuleException(selfRegistration.getVolunteer().getName() + " já se cadastrou com o link");
        }

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        UUID link;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
