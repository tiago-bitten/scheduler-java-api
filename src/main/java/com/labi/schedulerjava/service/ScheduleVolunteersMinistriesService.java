package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.*;
import com.labi.schedulerjava.repository.ScheduleVolunteersMinistriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleVolunteersMinistriesService {

    @Autowired
    private ScheduleVolunteersMinistriesRepository scheduleVolunteersMinistriesRepository;

    public void associateScheduleWithVolunteersMinistries(ScheduleGrid scheduleGrid, VolunteerMinistry volunteerMinistry) {
        ScheduleVolunteersMinistries scheduleVolunteersMinistries = new ScheduleVolunteersMinistries(scheduleGrid, volunteerMinistry);
        scheduleVolunteersMinistriesRepository.save(scheduleVolunteersMinistries);
    }

    public List<ScheduleVolunteersMinistries> findAllByScheduleId(Long scheduleId) {
        return scheduleVolunteersMinistriesRepository.findAll().stream()
                .filter(scheduleVolunteersMinistries -> scheduleVolunteersMinistries.getScheduleGrid().getId().equals(scheduleId))
                .toList();
    }
}
