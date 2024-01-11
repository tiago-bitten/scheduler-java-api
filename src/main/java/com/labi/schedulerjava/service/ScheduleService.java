package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.ScheduleGrid;
import com.labi.schedulerjava.domain.ScheduleVolunteersMinistries;
import com.labi.schedulerjava.domain.VolunteerMinistry;
import com.labi.schedulerjava.dtos.*;
import com.labi.schedulerjava.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private ScheduleVolunteersMinistriesService scheduleVolunteersMinistriesService;

    public void open(CreateScheduleDto dto) {
        if (dto.date().isBefore(LocalDate.now())) {
            throw new RuntimeException("Date cannot be in the past");
        }
        ScheduleGrid scheduleGrid = new ScheduleGrid(dto.name(), dto.description(), dto.date());
        scheduleRepository.save(scheduleGrid);
    }

    public void close(Long scheduleId) {
        ScheduleGrid scheduleGrid = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("ScheduleGrid not found"));
        scheduleGrid.setIsActive(false);
        scheduleRepository.save(scheduleGrid);
    }

    public void addVolunteer(Long scheduleId, Long volunteerId, Long ministryId) {
        ScheduleGrid scheduleGrid = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("ScheduleGrid not found"));
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.findByVolunteerAndMinistry(volunteerId, ministryId)
                .orElseThrow(() -> new RuntimeException("Volunteer Ministry not found"));

        if (!volunteerMinistry.getIsActive())
            throw new RuntimeException("Volunteer Ministry is not active");

        scheduleVolunteersMinistriesService.associateScheduleWithVolunteersMinistries(scheduleGrid, volunteerMinistry);
    }

    public ReadScheduleDto findAll(Long scheduleId) {
        ScheduleGrid scheduleGrid = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("ScheduleGrid not found"));

        List<ScheduleVolunteersMinistries> scheduleVolunteersMinistries =
                scheduleVolunteersMinistriesService.findAllByScheduleId(scheduleId);

        return new ReadScheduleDto(
                scheduleGrid.getId(),
                scheduleGrid.getName(),
                scheduleGrid.getDescription(),
                scheduleGrid.getDate(),
                scheduleVolunteersMinistries.stream()
                        .map(ScheduleVolunteersMinistries::getVolunteerMinistry)
                        .map(volunteerMinistry -> new ReadSimpVolunteerMinistry(
                                volunteerMinistry.getId(),
                                new ReadSimpVolunteerDto(
                                        volunteerMinistry.getVolunteer().getId(),
                                        volunteerMinistry.getVolunteer().getName(),
                                        volunteerMinistry.getVolunteer().getLastName(),
                                        volunteerMinistry.getVolunteer().getPhone(),
                                        volunteerMinistry.getVolunteer().getBirthDate(),
                                        new ReadMinistryDto(
                                                volunteerMinistry.getMinistry().getId(),
                                                volunteerMinistry.getMinistry().getName(),
                                                volunteerMinistry.getMinistry().getDescription()

                                        )
                                )
                        )).toList()
        );
    }
}
