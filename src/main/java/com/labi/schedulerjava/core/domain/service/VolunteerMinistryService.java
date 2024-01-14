package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VolunteerMinistryService {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    public VolunteerMinistry validateVolunteerMinistry(Long volunteer, Long ministry) {
        VolunteerMinistry volunteerMinistry = findByVolunteerAndMinistry(volunteer, ministry)
                .orElseThrow(() -> new BusinessRuleException("Vinculo voluntário e ministério não encontrado"));

        if (!volunteerMinistry.getIsActive())
            throw new BusinessRuleException("Voluntário não pode ser agendado neste ministério, pois o vinculo está inativo");

        return volunteerMinistry;
    }

    public Optional<VolunteerMinistry> findByVolunteerAndMinistry(Volunteer volunteer, Ministry ministry) {
        return volunteerMinistryRepository.findAll().stream()
                .filter(volunteerMinistry -> volunteerMinistry.getVolunteer().equals(volunteer) && volunteerMinistry.getMinistry().equals(ministry))
                .findFirst();
    }

    public Optional<VolunteerMinistry> findByVolunteerAndMinistry(Long volunteerId, Long ministryId) {
        return volunteerMinistryRepository.findAll().stream()
                .filter(volunteerMinistry -> volunteerMinistry.getVolunteer().getId().equals(volunteerId) && volunteerMinistry.getMinistry().getId().equals(ministryId))
                .findFirst();
    }
}
