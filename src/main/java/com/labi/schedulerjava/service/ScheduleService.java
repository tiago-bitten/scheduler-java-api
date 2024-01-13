package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Schedule;
import com.labi.schedulerjava.domain.Assignment;
import com.labi.schedulerjava.domain.VolunteerMinistry;
import com.labi.schedulerjava.dtos.*;
import com.labi.schedulerjava.enterprise.BusinessRuleException;
import com.labi.schedulerjava.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void open(CreateScheduleDto dto) {
        if (dto.startDate().isBefore(LocalDateTime.now()) || dto.endDate().isBefore(LocalDateTime.now()))
            throw new BusinessRuleException("A data de início e fim não podem ser anteriores a data atual");

        if (dto.startDate().isAfter(dto.endDate()))
            throw new BusinessRuleException("A data de início não pode ser posterior a data de fim");

        Schedule schedule = new Schedule(dto.name(), dto.description(), dto.startDate(), dto.endDate(), dto.weekNumber());
        scheduleRepository.save(schedule);
    }

    public void close(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar a agenda"));
        schedule.setIsActive(false);
        scheduleRepository.save(schedule);
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    public ReadScheduleDto findAll(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar a agenda"));

        if (!schedule.getIsActive()) throw new BusinessRuleException("Agenda não está ativa");

        List<Assignment> scheduleVolunteersMinistries = schedule.getAssignments();

        return new ReadScheduleDto(
                schedule.getId(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getWeekNumber(),
                scheduleVolunteersMinistries.stream()
                        .map(Assignment::getVolunteerMinistry)
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
