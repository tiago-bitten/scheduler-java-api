package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteVolunteerUseCase extends UseCase<DeleteVolunteerUseCase.InputValues, DeleteVolunteerUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = volunteerRepository.findById(input.id)
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.id + " não corresponde a um voluntário cadastrado"));

        volunteer.getVolunteerMinistries().forEach(vm -> {
            boolean isActive = vm.getIsActive();
            if (isActive)
                vm.getMinistry().setTotalVolunteers(vm.getMinistry().getTotalVolunteers() - 1);
        });

        volunteerRepository.delete(volunteer);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
