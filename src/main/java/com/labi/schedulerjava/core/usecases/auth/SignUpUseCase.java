package com.labi.schedulerjava.core.usecases.auth;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadUserDto;
import com.labi.schedulerjava.dtos.SignUpDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignUpUseCase extends UseCase<SignUpUseCase.InputValues, SignUpUseCase.OutputValues> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OutputValues execute(InputValues input) {
        if (userRepository.findByEmail(input.dto.email()).isPresent())
            throw new BusinessRuleException("Este e-mail já está cadastrado");

        User user = new User(input.dto.name(), input.dto.email(), input.dto.password());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new OutputValues(toDto(user));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        SignUpDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadUserDto user;
    }

    private ReadUserDto toDto(User user) {
        return new ReadUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getIsApproved(),
                user.getIsSuperUser(),
                user.getCreatedAt(),
                List.of()
        );
    }
}
