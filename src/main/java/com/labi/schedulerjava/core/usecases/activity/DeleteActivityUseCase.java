package com.labi.schedulerjava.core.usecases.activity;

import com.labi.schedulerjava.adapters.persistence.ActivityRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Activity;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteActivityUseCase extends UseCase<DeleteActivityUseCase.InputValues, DeleteActivityUseCase.OutputValues> {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        Activity activity = activityRepository.findById(input.id)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.id + " não corresponde a nenhuma atividade cadastrada"));

        if (!userMinistryService.existsUserMinistryRelation(user.getId(), activity.getMinistry().getId()))
            throw new BusinessRuleException("O usuário não tem permissão para deletar esta atividade");

        if (!activity.getIsActive())
            throw new BusinessRuleException("Esta atividade já está desativada");

        activity.setIsActive(false);
        activity.setName(activity.getName() + " (DELETED)");
        activityRepository.save(activity);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        Long id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
