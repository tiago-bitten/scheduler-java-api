package com.labi.schedulerjava.core.usecases.volunteerministry;

import com.labi.schedulerjava.adapters.persistence.VolunteerMinistryRepository;
import com.labi.schedulerjava.core.domain.Ministry;
import com.labi.schedulerjava.core.domain.Volunteer;
import com.labi.schedulerjava.core.domain.VolunteerMinistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindVolunteerMinistryByVolunteerAndMinistryUseCase {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

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
