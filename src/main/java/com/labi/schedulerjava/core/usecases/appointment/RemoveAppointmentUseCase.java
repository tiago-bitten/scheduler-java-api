package com.labi.schedulerjava.core.usecases.appointment;

import com.labi.schedulerjava.adapters.persistence.AppointmentRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveAppointmentUseCase extends UseCase<RemoveAppointmentUseCase.InputValues, RemoveAppointmentUseCase.OutputValues> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Appointment appointment = appointmentRepository.findById(input.appointmentId)
                .orElseThrow(() -> new BusinessRuleException("Não foi possível encontrar o agendamento"));

        appointmentRepository.delete(appointment);
        appointmentRepository.flush();

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long appointmentId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
