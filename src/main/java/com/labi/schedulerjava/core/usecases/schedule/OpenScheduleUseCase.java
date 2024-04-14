package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.CreateScheduleDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OpenScheduleUseCase extends UseCase<OpenScheduleUseCase.InputValues, OpenScheduleUseCase.OutputValues> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);

        if (input.dto.startDate().isBefore(LocalDateTime.now()) || input.dto.endDate().isBefore(LocalDateTime.now()))
            throw new BusinessRuleException("A data de início e fim não podem ser anteriores a data atual");

        if (input.dto.startDate().isAfter(input.dto.endDate()))
            throw new BusinessRuleException("A data de início não pode ser posterior a data de fim");

        Schedule schedule = new Schedule(input.dto.name(), input.dto.description(), input.dto.startDate(), input.dto.endDate(), user);
        scheduleRepository.save(schedule);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        CreateScheduleDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
