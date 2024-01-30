package com.labi.schedulerjava.core.usecases.user;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserApproveService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApproveUserUseCase extends UseCase<ApproveUserUseCase.InputValues, ApproveUserUseCase.OutputValues> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserApproveService userApproveService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OutputValues execute(InputValues input) {
        User userApprover = jwtTokenProvider.getUserFromToken(input.authHeader);
        User userToApprove = userRepository.findById(input.userToApproveId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.userToApproveId + " não corresponde a um usuário cadastrado"));

        if (!userApprover.getIsApproved() && !userApprover.getIsSuperUser())
            throw new BusinessRuleException("O usuário aprovador não está aprovado ou não possui permissão para aprovar usuários");

        if (userToApprove.getIsApproved())
            throw new BusinessRuleException("O usuário já está aprovado");

        userToApprove.setIsApproved(true);
        userToApprove.setIsSuperUser(input.isSuperUser);
        userRepository.save(userToApprove);

        userApproveService.register(userToApprove, userApprover);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        Long userToApproveId;
        Boolean isSuperUser;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
