package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.domain.VolunteerMinistry;
import com.labi.schedulerjava.repository.VolunteerMinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerMinistryService {

    @Autowired
    private VolunteerMinistryRepository volunteerMinistryRepository;

    public void save(Volunteer volunteer, Ministry ministry) {
        VolunteerMinistry volunteerMinistry = new VolunteerMinistry(volunteer, ministry);
        volunteerMinistryRepository.save(volunteerMinistry);
    }
}
