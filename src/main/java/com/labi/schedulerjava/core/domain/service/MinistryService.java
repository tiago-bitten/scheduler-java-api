package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MinistryService {

    @Autowired
    private MinistryRepository ministryRepository;

    public Optional<Ministry> findById(Long id) {
        return ministryRepository.findById(id);
    }
}
