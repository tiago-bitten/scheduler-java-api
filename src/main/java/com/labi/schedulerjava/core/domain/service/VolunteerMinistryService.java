package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerMinistryService {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private VolunteerService volunteerService;

    public VolunteerMinistry validateVolunteerMinistry(Long volunteer, Long ministry) {
        VolunteerMinistry volunteerMinistry = findByVolunteerAndMinistry(volunteer, ministry)
                .orElseThrow(() -> new BusinessRuleException("Vinculo voluntário e ministério não encontrado"));

        if (!volunteerMinistry.getIsActive())
            throw new BusinessRuleException("Voluntário não pode ser agendado neste ministério, pois o vinculo está inativo");

        return volunteerMinistry;
    }

    public Optional<VolunteerMinistry> findByVolunteerAndMinistry(Volunteer volunteer, Ministry ministry) {
        if (volunteerService.findById(volunteer.getId()).isEmpty())
            throw new BusinessRuleException("O ID informando " + volunteer.getId() + " não corresponde a um voluntário cadastrado");
        if (ministryService.findById(ministry.getId()).isEmpty())
            throw new BusinessRuleException("O ID informando " + ministry.getId() + " não corresponde a um ministério cadastrado");

        return volunteerMinistryRepository.findByVolunteerAndMinistry(volunteer, ministry);
    }

    public Optional<VolunteerMinistry> findByVolunteerAndMinistry(Long volunteerId, Long ministryId) {
        Volunteer volunteer = volunteerService.findById(volunteerId)
                .orElseThrow(() -> new BusinessRuleException("O ID informando " + volunteerId + " não corresponde a um voluntário cadastrado"));
        Ministry ministry = ministryService.findById(ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID informando " + ministryId + " não corresponde a um ministério cadastrado"));

        return volunteerMinistryRepository.findByVolunteerAndMinistry(volunteer, ministry);
    }
}
