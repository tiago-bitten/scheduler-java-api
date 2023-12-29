package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.repository.MinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinistryService {

    @Autowired
    private MinistryRepository ministryRepository;

    @Autowired
    private VolunteerService volunteerService;

    public void create(CreateMinistryDto dto) {
        Ministry ministry = new Ministry(dto.name(), dto.description());
        ministryRepository.save(ministry);
    }

    public void addVolunteer(Long ministryId, Long volunteerId) {
        Ministry ministry = ministryRepository.findById(ministryId).orElseThrow(() -> new RuntimeException("Ministry not found"));
        Volunteer volunteer = volunteerService.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"));
        ministry.addVolunteer(volunteer);
        ministryRepository.save(ministry);
    }
}
