package com.labi.schedulerjava.core.usecases.user;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindUserMinistriesUseCase extends UseCase<FindUserMinistriesUseCase.InputValues, FindUserMinistriesUseCase.OutputValues> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MinistryService ministryService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        return new OutputValues(
                ministryService.findAllByUserId(user.getId()).stream()
                        .map((ministry) -> new ReadMinistryDto(ministry.getId(), ministry.getName(), ministry.getDescription(), ministry.getColor(), ministry.getTotalVolunteers()))
                        .toList()
        );
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadMinistryDto> ministries;
    }
}
