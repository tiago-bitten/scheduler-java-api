package com.labi.schedulerjava.service;

import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.dtos.*;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class _ScheduleService {

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

        if (!schedule.getIsActive())
            throw new BusinessRuleException("A agenda já está fechada");

        schedule.setIsActive(false);
        scheduleRepository.save(schedule);
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<ReadSimpScheduleDto> findSchedules(Integer month, Integer year) {
        List<Schedule> schedules = scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getStartDate().getMonthValue() == month && schedule.getStartDate().getYear() == year)
                .toList();

        return schedules.stream()
                .map(schedule -> new ReadSimpScheduleDto(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        schedule.getWeekNumber(),
                        schedule.getIsActive()
                )).toList();
    }

    public ReadScheduleDto findScheduleAppointments(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar a agenda"));

        if (!schedule.getIsActive()) throw new BusinessRuleException("Agenda não está ativa");

        List<Appointment> appointments = schedule.getAppointments();

        return new ReadScheduleDto(
                schedule.getId(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getWeekNumber(),
                appointments.stream()
                        .map(Appointment::getVolunteerMinistry)
                        .map(volunteerMinistry -> new ReadVolunteerMinistry(
                                volunteerMinistry.getId(),
                                volunteerMinistry.getIsActive(),
                                new ReadSimpVolunteerDto(
                                        volunteerMinistry.getVolunteer().getId(),
                                        volunteerMinistry.getVolunteer().getName(),
                                        volunteerMinistry.getVolunteer().getLastName(),
                                        volunteerMinistry.getVolunteer().getPhone(),
                                        volunteerMinistry.getVolunteer().getBirthDate()
                                ),
                                new ReadMinistryDto(
                                        volunteerMinistry.getMinistry().getId(),
                                        volunteerMinistry.getMinistry().getName(),
                                        volunteerMinistry.getMinistry().getDescription()

                                )
                        )).toList()
        );
    }
}
