package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.service.ScheduleService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReOpenScheduleUseCase extends UseCase<ReOpenScheduleUseCase.InputValues, ReOpenScheduleUseCase.OutputValues> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public OutputValues execute(InputValues input) {
        Schedule schedule = scheduleService.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Schedule not found"));

        schedule.setIsActive(true);
        scheduleRepository.save(schedule);

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
