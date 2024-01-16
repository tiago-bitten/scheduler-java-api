package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadSimpScheduleDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindSchedulesUseCase extends UseCase<FindSchedulesUseCase.InputValues, FindSchedulesUseCase.OutputValues> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public OutputValues execute(InputValues input) {
        List<Schedule> schedules = scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getStartDate().getMonthValue() == input.month && schedule.getStartDate().getYear() == input.year)
                .toList();

        return new OutputValues(schedules.stream()
                .map(schedule -> new ReadSimpScheduleDto(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        schedule.getWeekNumber(),
                        schedule.getIsActive()
                )).toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private Integer month;
        private Integer year;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private List<ReadSimpScheduleDto> schedules;
    }
}
