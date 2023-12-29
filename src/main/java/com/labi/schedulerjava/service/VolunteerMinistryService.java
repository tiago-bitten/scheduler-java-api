package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.domain.VolunteerMinistry;
import com.labi.schedulerjava.repository.VolunteerMinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VolunteerMinistryService {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    public void associateVolunteerWithMinistry(Volunteer volunteer, Ministry ministry) {
        VolunteerMinistry volunteerMinistry = findExistingOrNewVolunteerMinistry(volunteer, ministry);
        if (!volunteerMinistry.getIsActive()) {
            volunteerMinistry.setIsActive(true);
            volunteerMinistryRepository.save(volunteerMinistry);
        } else {
            throw new RuntimeException("Volunteer is already active in this ministry.");
        }
    }

    private VolunteerMinistry findExistingOrNewVolunteerMinistry(Volunteer volunteer, Ministry ministry) {
        return volunteer.getVolunteerMinistries().stream()
                .filter(volunteerMinistry -> volunteerMinistry.getMinistry().equals(ministry))
                .findFirst()
                .orElseGet(() -> new VolunteerMinistry(volunteer, ministry, true));
    }

    public Optional<VolunteerMinistry> findById(Long id) {
        return volunteerMinistryRepository.findById(id);
    }
}
