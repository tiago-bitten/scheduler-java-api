package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.ScheduleService;
import com.labi.schedulerjava.core.domain.service.UnavailableDateService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import com.labi.schedulerjava.dtos.ReadVolunteersToAppointDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindVolunteersNotInScheduleUseCase extends UseCase<FindVolunteersNotInScheduleUseCase.InputValues, FindVolunteersNotInScheduleUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Override
    public OutputValues execute(InputValues input) {
        List<Volunteer> volunteers = volunteerRepository.findAll(input.ministryId);
        Schedule schedule = scheduleService.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + input.scheduleId + " não corresponde a um horário cadastrado"));

        List<Volunteer> filteredVolunteers = volunteers.stream()
                .filter(volunteer -> volunteer.getVolunteerMinistries().stream()
                        .filter(VolunteerMinistry::getIsActive)
                        .flatMap(volunteerMinistry -> volunteerMinistry.getAppointments().stream())
                        .noneMatch(appointment -> appointment.getSchedule().equals(schedule)))
                .toList();

        List<ReadVolunteersToAppointDto> volunteerDtos = filteredVolunteers.stream()
                .map(volunteer -> toDto(schedule, volunteer))
                .toList();

        return new OutputValues(volunteerDtos);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadVolunteersToAppointDto> volunteers;
    }

    private ReadVolunteersToAppointDto toDto(Schedule schedule, Volunteer volunteer) {
        Boolean isUnavailable = unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId());

        return new ReadVolunteersToAppointDto(
                volunteer.getId(),
                isUnavailable,
                volunteer.getName(),
                volunteer.getLastName(),
                volunteer.getCpf(),
                volunteer.getPhone(),
                volunteer.getBirthDate()
        );
    }
}
