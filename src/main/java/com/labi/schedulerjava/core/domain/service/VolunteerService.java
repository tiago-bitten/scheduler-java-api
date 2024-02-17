package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    public void save(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

    public Optional<Volunteer> findByAccessKey(UUID accessKey) {
        return volunteerRepository.findByAccessKey(accessKey);
    }

    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    public List<Volunteer> findAll(Specification<Volunteer> spec) {
        return volunteerRepository.findAll(spec);
    }
}
