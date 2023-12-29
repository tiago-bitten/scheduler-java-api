package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Schedule;
import com.labi.schedulerjava.domain.VolunteerMinistry;
import com.labi.schedulerjava.dtos.CreateScheduleDto;
import com.labi.schedulerjava.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private ScheduleVolunteersMinistriesService scheduleVolunteersMinistriesService;

    public void create(CreateScheduleDto dto) {
        if (dto.date().isBefore(LocalDate.now())) {
            throw new RuntimeException("Date cannot be in the past");
        }
        Schedule schedule = new Schedule(dto.name(), dto.description(), dto.date());
        scheduleRepository.save(schedule);
    }

    public void addVolunteer(Long scheduleId, Long volunteerMinistryId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.findById(volunteerMinistryId)
                .orElseThrow(() -> new RuntimeException("Volunteer Ministry not found"));

        if (!volunteerMinistry.getIsActive())
            throw new RuntimeException("Volunteer Ministry is not active");

        scheduleVolunteersMinistriesService.save(schedule, volunteerMinistry);
    }
}
