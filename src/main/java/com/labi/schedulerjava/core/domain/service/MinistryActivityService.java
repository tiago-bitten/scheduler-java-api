package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.MinistryActivitiesRepository;
import com.labi.schedulerjava.core.domain.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MinistryActivityService {

    @Autowired
    private MinistryActivitiesRepository ministryActivitiesRepository;

    public Optional<Activity> findById(Long id) {
        return ministryActivitiesRepository.findById(id);
    }
}
