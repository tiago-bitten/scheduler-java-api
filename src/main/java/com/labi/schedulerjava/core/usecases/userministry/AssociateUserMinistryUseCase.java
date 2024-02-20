package com.labi.schedulerjava.core.usecases.userministry;

import com.labi.schedulerjava.adapters.persistence.UserMinistryRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.UserMinistry;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.domain.service.UserService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssociateUserMinistryUseCase extends UseCase<AssociateUserMinistryUseCase.InputValues, AssociateUserMinistryUseCase.OutputValues> {

    @Autowired
    private UserMinistryRepository userMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private UserService userService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = userService.findById(input.userId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.userId + " não corresponde a um usuário cadastrado"));

        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.ministryId + " não corresponde a um ministério cadastrado"));

        UserMinistry userMinistry = new UserMinistry(user, ministry);
        userMinistryRepository.save(userMinistry);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long userId;
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
