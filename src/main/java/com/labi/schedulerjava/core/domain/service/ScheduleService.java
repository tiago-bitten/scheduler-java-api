package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.SchedulerJavaApplication;
import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }
}
