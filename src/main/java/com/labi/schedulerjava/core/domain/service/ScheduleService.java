package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule validateSchedule(Long shceduleId) {
        Schedule schedule = findById(shceduleId)
                .orElseThrow(() -> new BusinessRuleException("Agenda não encontrada"));

        if (!schedule.getIsActive())
            throw new BusinessRuleException("Agenda não está ativa");

        return schedule;
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }
}
