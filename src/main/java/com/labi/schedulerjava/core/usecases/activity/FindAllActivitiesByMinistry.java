package com.labi.schedulerjava.core.usecases.activity;

import com.labi.schedulerjava.adapters.persistence.ActivityRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Activity;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ActivityResponse;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllActivitiesByMinistry extends UseCase<FindAllActivitiesByMinistry.InputValues, FindAllActivitiesByMinistry.OutputValues> {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MinistryService ministryService;


    @Override
    public OutputValues execute(InputValues input) {
        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.ministryId + " não corresponde a nenhum ministério cadastrado"));

        List<Activity> activities = activityRepository.findAllByMinistryIdAndIsActiveTrue(ministry.getId());

        return new OutputValues(activities.stream().map(this::toDto).toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ActivityResponse> activities;
    }

    private ActivityResponse toDto(Activity activity) {
        return new ActivityResponse(activity.getId(), activity.getName(), activity.getDefaultTotalVolunteers());
    }
}
