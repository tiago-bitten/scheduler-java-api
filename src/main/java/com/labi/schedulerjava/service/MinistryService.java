package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.repository.MinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MinistryService {

    @Autowired
    private MinistryRepository ministryRepository;

    public void create(CreateMinistryDto dto) {
        if (ministryRepository.findByName(dto.name()).isPresent())
            throw new RuntimeException("Ministry already exists");

        Ministry ministry = new Ministry(dto.name(), dto.description());
        ministryRepository.save(ministry);
    }

    public Optional<Ministry> findById(Long id) {
        return ministryRepository.findById(id);
    }

    public Optional<Ministry> findByName(String name) {
        return ministryRepository.findByName(name);
    }
}
