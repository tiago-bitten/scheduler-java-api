package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindScheduleByIdUseCase {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }
}
