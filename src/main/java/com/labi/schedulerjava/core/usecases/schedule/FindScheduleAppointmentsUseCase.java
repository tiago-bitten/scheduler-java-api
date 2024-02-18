package com.labi.schedulerjava.core.usecases.schedule;

import com.labi.schedulerjava.adapters.persistence.ScheduleRepository;
import com.labi.schedulerjava.adapters.persistence.specification.AppointmentSpecification;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.domain.model.Schedule;
import com.labi.schedulerjava.core.domain.service.AppointmentService;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.*;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindScheduleAppointmentsUseCase extends UseCase<FindScheduleAppointmentsUseCase.InputValues, FindScheduleAppointmentsUseCase.OutputValues> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public OutputValues execute(InputValues input) {
        Schedule schedule = scheduleRepository.findById(input.scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar a agenda"));

        if (!schedule.getIsActive()) throw new BusinessRuleException("Agenda não está ativa");

        Specification<Appointment> spec = Specification.where(AppointmentSpecification.hasSchedule(input.scheduleId))
                .and(AppointmentSpecification.hasVolunteer(input.volunteerName));

        List<Appointment> appointments = appointmentService.findAll(spec);

        return new OutputValues(
                new ReadScheduleDto(
                schedule.getId(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getWeekNumber(),
                appointments.stream()
                        .map(appointment -> new ReadAppointmentsDto(
                                appointment.getId(),
                                new ReadVolunteerMinistry(
                                        appointment.getVolunteerMinistry().getId(),
                                        appointment.getVolunteerMinistry().getIsActive(),
                                        new ReadSimpVolunteerDto(
                                                appointment.getVolunteerMinistry().getVolunteer().getId(),
                                                appointment.getVolunteerMinistry().getVolunteer().getAccessKey(),
                                                appointment.getVolunteerMinistry().getVolunteer().getName(),
                                                appointment.getVolunteerMinistry().getVolunteer().getLastName(),
                                                appointment.getVolunteerMinistry().getVolunteer().getCpf(),
                                                appointment.getVolunteerMinistry().getVolunteer().getPhone(),
                                                appointment.getVolunteerMinistry().getVolunteer().getBirthDate(),
                                                appointment.getVolunteerMinistry().getVolunteer().getOrigin()
                                        ),
                                        new ReadMinistryDto(
                                                appointment.getVolunteerMinistry().getMinistry().getId(),
                                                appointment.getVolunteerMinistry().getMinistry().getName(),
                                                appointment.getVolunteerMinistry().getMinistry().getDescription(),
                                                appointment.getVolunteerMinistry().getMinistry().getColor(),
                                                appointment.getVolunteerMinistry().getMinistry().getTotalVolunteers()
                                        )
                                )
                        )).toList()
        ));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
        String volunteerName;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadScheduleDto schedule;
    }
}
