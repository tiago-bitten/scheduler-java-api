package com.labi.schedulerjava.core.usecases.volunteerministry;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMinistryService userMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.authHeader);
        if (!userMinistryService.checkUserMinistryRelation(user.getId(), input.ministryId))
            throw new BusinessRuleException("Você não tem permissão para associar voluntários a este ministério");

        VolunteerMinistry volunteerMinistry;
        Optional<VolunteerMinistry> existsVolunteerMinistry =
                volunteerMinistryService.findByVolunteerAndMinistry(input.volunteerId, input.ministryId);

        if (existsVolunteerMinistry.isEmpty()) {
            Volunteer volunteer = volunteerService.findById(input.volunteerId).get();
            Ministry ministry = ministryService.findById(input.ministryId).get();

            volunteerMinistry = new VolunteerMinistry(volunteer, ministry);
            ministry.setTotalVolunteers(ministry.getTotalVolunteers() + 1);
            volunteerMinistryRepository.save(volunteerMinistry);

            return new OutputValues();
        }

        volunteerMinistry = existsVolunteerMinistry.get();

        if (volunteerMinistry.getIsActive())
            throw new BusinessRuleException("O voluntário já está vinculado a este ministério");

        volunteerMinistry.setIsActive(true);
        volunteerMinistry.getMinistry().setTotalVolunteers(volunteerMinistry.getMinistry().getTotalVolunteers() + 1);
        volunteerMinistryRepository.save(volunteerMinistry);

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long volunteerId;
        Long ministryId;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
    }
}
