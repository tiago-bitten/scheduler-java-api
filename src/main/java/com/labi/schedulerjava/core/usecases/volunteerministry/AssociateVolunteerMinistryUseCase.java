package com.labi.schedulerjava.core.usecases.volunteerministry;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.MinistryService;
import com.labi.schedulerjava.core.domain.service.VolunteerMinistryService;
import com.labi.schedulerjava.core.domain.service.VolunteerService;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssociateVolunteerMinistryUseCase extends UseCase<AssociateVolunteerMinistryUseCase.InputValues, AssociateVolunteerMinistryUseCase.OutputValues> {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        Volunteer volunteer = volunteerService.findById(input.volunteerId)
                .orElseThrow(() -> new BusinessRuleException("O ID informando " + input.volunteerId + " não corresponde a um voluntário cadastrado"));
        Ministry ministry = ministryService.findById(input.ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID informando " + input.ministryId + " não corresponde a um ministério cadastrado"));

        VolunteerMinistry volunteerMinistry =
                volunteerMinistryService.findByVolunteerAndMinistry(volunteer, ministry).orElse(null);

        if (volunteerMinistry != null && volunteerMinistry.getIsActive())
            throw new BusinessRuleException("O voluntário já está vinculado a este ministério");

        if (volunteerMinistry != null) {
            volunteerMinistry.setIsActive(true);
        } else {
            volunteerMinistry = new VolunteerMinistry(volunteer, ministry);
        }
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
