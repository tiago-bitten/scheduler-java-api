package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MinistryService {

    @Autowired
    private MinistryRepository ministryRepository;

    public List<Ministry> findAllByUserId(Long userId) {
        return ministryRepository.findAllByUserId(userId);
    }

    public Optional<Ministry> findById(Long id) {
        return ministryRepository.findById(id);
    }
}
