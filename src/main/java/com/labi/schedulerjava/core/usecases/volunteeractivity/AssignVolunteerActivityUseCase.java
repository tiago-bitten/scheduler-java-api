package com.labi.schedulerjava.core.usecases.volunteeractivity;

import com.labi.schedulerjava.adapters.persistence.VolunteerActivityRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Activity;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerActivity;
import com.labi.schedulerjava.core.domain.service.MinistryActivityService;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignVolunteerActivityUseCase extends UseCase<AssignVolunteerActivityUseCase.InputValues, AssignVolunteerActivityUseCase.OutputValues> {

    @Autowired
    private VolunteerActivityRepository volunteerActivityRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private MinistryActivityService ministryActivityService;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = volunteerService.findById(input.volunteerId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.volunteerId + " não corresponde a um voluntário cadastrado"));

        Activity activity = ministryActivityService.findById(input.activityId)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.activityId + " não corresponde a uma atividade cadastrada"));

        VolunteerActivity volunteerActivity = new VolunteerActivity(volunteer, activity);
        volunteerActivityRepository.save(volunteerActivity);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long volunteerId;
        Long activityId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
