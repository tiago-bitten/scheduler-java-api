package com.labi.schedulerjava.core.usecases.volunteerministry;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.domain.service.VolunteerMinistryService;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisassociateVolunteerMinistryUseCase extends UseCase<DisassociateVolunteerMinistryUseCase.InputValues, DisassociateVolunteerMinistryUseCase.OutputValues> {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.findByVolunteerAndMinistry(input.volunteerId, input.ministryId)
                        .orElseThrow(() -> new BusinessRuleException("Não existe vínculo entre o voluntário e o ministério"));

        if (!volunteerMinistry.getIsActive())
            throw new BusinessRuleException("O vínculo entre o voluntário e o ministério já está inativo");

        volunteerMinistry.setIsActive(false);
        volunteerMinistry.getMinistry().setTotalVolunteers(volunteerMinistry.getMinistry().getTotalVolunteers() - 1);
        volunteerMinistryRepository.save(volunteerMinistry);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long volunteerId;
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
