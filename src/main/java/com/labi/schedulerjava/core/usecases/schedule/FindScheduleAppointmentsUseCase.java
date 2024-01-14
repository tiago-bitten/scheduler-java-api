package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadScheduleDto;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import com.labi.schedulerjava.dtos.ReadVolunteerMinistry;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindScheduleAppointmentsUseCase extends UseCase<FindScheduleAppointmentsUseCase.InputValues, FindScheduleAppointmentsUseCase.OutputValues> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Schedule schedule = scheduleRepository.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar a agenda"));

        if (!schedule.getIsActive()) throw new BusinessRuleException("Agenda não está ativa");

        List<Appointment> appointments = schedule.getAppointments();

        return new OutputValues(
                new ReadScheduleDto(
                schedule.getId(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getWeekNumber(),
                appointments.stream()
                        .map(Appointment::getVolunteerMinistry)
                        .map(volunteerMinistry -> new ReadVolunteerMinistry(
                                volunteerMinistry.getId(),
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
        ));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private Long scheduleId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private ReadScheduleDto dto;
    }
}
