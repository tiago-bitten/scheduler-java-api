package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.core.domain.Appointment;
import com.labi.schedulerjava.core.domain.Schedule;
import com.labi.schedulerjava.core.domain.User;
import com.labi.schedulerjava.core.domain.VolunteerMinistry;
import com.labi.schedulerjava.core.usecases.schedule.FindScheduleByIdUseCase;
import com.labi.schedulerjava.core.usecases.user.FindUserByIdUseCaseUseCase;
import com.labi.schedulerjava.core.usecases.volunteerministry.FindVolunteerMinistryByVolunteerAndMinistryUseCase;
import com.labi.schedulerjava.enterprise.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private FindUserByIdUseCaseUseCase findUserByIdUseCaseUseCase;

    @Autowired
    private FindScheduleByIdUseCase findScheduleByIdUseCase;

    @Autowired
    private FindVolunteerMinistryByVolunteerAndMinistryUseCase findVolunteerMinistryByVolunteerAndMinistryUseCase;

    public void create(Long scheduleId, Long volunteerId, Long ministryId, Long userId) {
        User user = findUserByIdUseCaseUseCase.findById(userId)
                .orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));
        Schedule schedule = findScheduleByIdUseCase.findById(scheduleId)
                .orElseThrow(() -> new BusinessRuleException("Agenda não encontrada"));
        VolunteerMinistry volunteerMinistry = findVolunteerMinistryByVolunteerAndMinistryUseCase.findByVolunteerAndMinistry(volunteerId, ministryId)
                .orElseThrow(() -> new BusinessRuleException("Vinculo entre voluntário e ministério não encontrado"));

        Optional<Appointment> existsAssignment = schedule.getAppointments().stream()
                .filter(assignment -> assignment.getVolunteerMinistry().getVolunteer().getId().equals(volunteerId))
                .findFirst();

        if (existsAssignment.isPresent())
            throw new BusinessRuleException("Voluntário já está vinculado a esta agenda");

        Appointment appointment = new Appointment(schedule, volunteerMinistry, user);
        appointmentRepository.save(appointment);
    }
}
