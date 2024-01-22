package com.labi.schedulerjava.core.usecases.auth;

import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.UserAccountNotApprovedException;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.SignInDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SignInUseCase extends UseCase<SignInUseCase.InputValues, SignInUseCase.OutputValues> {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Override
    public OutputValues execute(InputValues input) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(input.dto.email(), input.dto.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        User user = userService.findByEmail(input.dto.email()).get();

        if (!user.getIsApproved()) {
            authentication.setAuthenticated(false);
            throw new UserAccountNotApprovedException();
        }

        return new OutputValues(jwtTokenProvider.generateToken(user));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        SignInDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        String token;
    }
}
