package com.labi.schedulerjava.core.usecases.user;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadUserDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindUsersToApproveUseCase extends UseCase<FindUsersToApproveUseCase.InputValues, FindUsersToApproveUseCase.OutputValues> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(userRepository.usersToApprove().stream()
                .map(user -> new ReadUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getIsApproved(),
                        user.getCreatedAt(),
                        user.getUserMinistries().stream()
                                .map(userMinistry -> new ReadMinistryDto(
                                        userMinistry.getMinistry().getId(),
                                        userMinistry.getMinistry().getName(),
                                        userMinistry.getMinistry().getDescription()
                                )).toList()
                )).toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private List<ReadUserDto> users;
    }
}
