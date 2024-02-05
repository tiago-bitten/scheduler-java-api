package com.labi.schedulerjava.core.usecases.selfregistration;

import com.labi.schedulerjava.adapters.persistence.SelfRegistrationRepository;
import com.labi.schedulerjava.core.domain.model.SelfRegistration;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateLinkUseCase extends UseCase<CreateLinkUseCase.InputValues, CreateLinkUseCase.OutputValues> {

    @Autowired
    private SelfRegistrationRepository selfRegistrationRepository;

    @Override
    public OutputValues execute(InputValues input) {
        SelfRegistration selfRegistration = new SelfRegistration(UUID.randomUUID());
        selfRegistrationRepository.save(selfRegistration);

        return new OutputValues(selfRegistration.getLink());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        UUID link;
    }
}
