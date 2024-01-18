package com.labi.schedulerjava.core.usecases.auth;

import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadTokenDto;
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

    @Override
    public OutputValues execute(InputValues input) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(input.dto.email(), input.dto.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        return new OutputValues(new ReadTokenDto(jwtTokenProvider.generateToken(input.dto.email())));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        SignInDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadTokenDto token;
    }
}
