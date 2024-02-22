package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.ActivityRepository;
import com.labi.schedulerjava.core.domain.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }
}
