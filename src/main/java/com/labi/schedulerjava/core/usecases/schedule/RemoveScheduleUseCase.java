package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveScheduleUseCase extends UseCase<RemoveScheduleUseCase.InputValues, RemoveScheduleUseCase.OutputValues> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Schedule schedule = scheduleRepository.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar a agenda"));

        scheduleRepository.delete(schedule);
        scheduleRepository.flush();

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
