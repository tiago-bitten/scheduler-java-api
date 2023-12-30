package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.*;
import com.labi.schedulerjava.repository.ScheduleVolunteersMinistriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleVolunteersMinistriesService {

    @Autowired
    private ScheduleVolunteersMinistriesRepository scheduleVolunteersMinistriesRepository;

    public void associateScheduleWithVolunteersMinistries(Schedule schedule, VolunteerMinistry volunteerMinistry) {
        ScheduleVolunteersMinistries scheduleVolunteersMinistries = new ScheduleVolunteersMinistries(schedule, volunteerMinistry);
        scheduleVolunteersMinistriesRepository.save(scheduleVolunteersMinistries);
    }

    public List<ScheduleVolunteersMinistries> findAllByScheduleId(Long scheduleId) {
        return scheduleVolunteersMinistriesRepository.findAll().stream()
                .filter(scheduleVolunteersMinistries -> scheduleVolunteersMinistries.getSchedule().getId().equals(scheduleId))
                .toList();
    }
}
