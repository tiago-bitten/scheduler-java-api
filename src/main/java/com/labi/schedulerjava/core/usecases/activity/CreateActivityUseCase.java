package com.labi.schedulerjava.core.usecases.activity;

import com.labi.schedulerjava.adapters.persistence.ActivityRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Activity;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ActivityRequest;
import com.labi.schedulerjava.dtos.ActivityResponse;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateActivityUseCase extends UseCase<CreateActivityUseCase.InputValues, CreateActivityUseCase.OutputValues> {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MinistryService ministryService;

    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID" + input.ministryId + "não corresponde a um ministério cadastrado"));

        if (input.request.totalVolunteers() < 1)
            throw new BusinessRuleException("O total de voluntários deve ser maior que 0");

        Activity activity = new Activity(input.request.name(), input.request.totalVolunteers(), ministry);

        activityRepository.save(activity);

        return new OutputValues(toDto(activity));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
        ActivityRequest request;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ActivityResponse activity;
    }

    private ActivityResponse toDto(Activity ma) {
        return new ActivityResponse(ma.getId(), ma.getName(), ma.getDefaultTotalVolunteers());
    }
}
