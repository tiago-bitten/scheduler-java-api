package com.labi.schedulerjava.service;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class _MinistryService {

    @Autowired
    private MinistryRepository ministryRepository;

    public void create(CreateMinistryDto dto) {
        if (ministryRepository.findByName(dto.name()).isPresent())
            throw new BusinessRuleException("Este ministério já está cadastrado");

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
