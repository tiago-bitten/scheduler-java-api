package com.labi.schedulerjava.service;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class _VolunteerMinistryService {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    public void associate(Volunteer volunteer, Ministry ministry) {
        VolunteerMinistry volunteerMinistry = findByVolunteerAndMinistry(volunteer, ministry).orElse(null);

        if (volunteerMinistry != null && volunteerMinistry.getIsActive())
            throw new BusinessRuleException("O voluntário já está vinculado a este ministério");

        if (volunteerMinistry != null) {
            volunteerMinistry.setIsActive(true);
            volunteerMinistryRepository.save(volunteerMinistry);
        } else {
            volunteerMinistry = new VolunteerMinistry(volunteer, ministry);
            volunteerMinistryRepository.save(volunteerMinistry);
        }
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

    public Optional<VolunteerMinistry> findById(Long id) {
        return volunteerMinistryRepository.findById(id);
    }
}
