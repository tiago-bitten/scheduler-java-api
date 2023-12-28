package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public void create(CreateVolunteerDto dto) {
        Volunteer volunteer = new Volunteer();
        volunteerRepository.save(volunteer);
    }
}
