package com.labi.schedulerjava.core.usecases.activity;

import com.labi.schedulerjava.adapters.persistence.ActivityRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Activity;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.service.UserMinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ActivityRequest;
import com.labi.schedulerjava.dtos.ActivityResponse;
import com.labi.schedulerjava.dtos.UpdateActivityRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateActivityUseCase extends UseCase<UpdateActivityUseCase.InputValues, UpdateActivityUseCase.OutputValues> {

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

        if (!userMinistryService.existsUserMinistryRelation(activity.getMinistry().getId(), user.getId()))
            throw new BusinessRuleException("O usuário não tem permissão para atualizar esta atividade");

        activity.setName(input.request.name());
        activity.setDefaultTotalVolunteers(input.request.defaultTotalVolunteers());

        activityRepository.save(activity);

        return new OutputValues(new ActivityResponse(activity.getId(), activity.getName(), activity.getDefaultTotalVolunteers()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        Long id;
        UpdateActivityRequest request;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ActivityResponse response;
    }
}
