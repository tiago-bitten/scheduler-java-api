package com.labi.schedulerjava.core.usecases.userministry;

import com.labi.schedulerjava.adapters.persistence.UserMinistryRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.UserMinistry;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;

public class AssociateUserMinistryUseCase extends UseCase<AssociateUserMinistryUseCase.InputValues, AssociateUserMinistryUseCase.OutputValues> {

    @Autowired
    private UserMinistryRepository userMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.ministryId + " não corresponde a um ministério cadastrado"));

        UserMinistry userMinistry = new UserMinistry(user, ministry);
        userMinistryRepository.save(userMinistry);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
