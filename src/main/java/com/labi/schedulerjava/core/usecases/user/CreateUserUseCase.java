package com.labi.schedulerjava.core.usecases.user;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateUserDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase extends UseCase<CreateUserUseCase.InputValues, CreateUserUseCase.OutputValues> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMinistryService userMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        if (userRepository.findByEmail(input.dto.email()).isPresent()) {
            throw new BusinessRuleException("Este e-mail já está cadastrado");
        }

        userMinistryService.validateMinistries(input.dto.ministries());

        User user = new User(input.dto.name(), input.dto.email(), input.dto.password());
        userRepository.save(user);
        userMinistryService.associate(user, input.dto.ministries());

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private CreateUserDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
