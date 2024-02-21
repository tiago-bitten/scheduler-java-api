package com.labi.schedulerjava.core.usecases.ministryactivities;

import com.labi.schedulerjava.adapters.persistence.MinistryActivitiesRepository;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateActivityUseCase extends UseCase<CreateActivityUseCase.InputValues, CreateActivityUseCase.OutputValues> {

    @Autowired
    private MinistryActivitiesRepository ministryActivitiesRepository;

    @Override
    public OutputValues execute(InputValues input) {
        return null;
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
