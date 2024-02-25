package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteMinistryUseCase extends UseCase<DeleteMinistryUseCase.InputValues, DeleteMinistryUseCase.OutputValues> {

    @Autowired
    private MinistryRepository ministryRepository;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryRepository.findById(input.getId())
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.getId() + " não corresponde a um ministério cadastrado"));

        User user = jwtTokenProvider.getUserFromToken(input.getAuthHeader());

        if (userMinistryService.existsUserMinistryRelation(ministry.getId(), user.getId())) {
            throw new BusinessRuleException("O usuário não tem permissão para deletar este ministério");
        }

        ministryRepository.delete(ministry);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long id;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
