package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.VolunteerLogRepository;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerLog;
import com.labi.schedulerjava.enums.Actions;
import com.labi.schedulerjava.enums.ActionsOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerLogService {

    @Autowired
    private VolunteerLogRepository volunteerLogRepository;

    public void log(Volunteer volunteer, Actions action, ActionsOrigin origin) {
        volunteerLogRepository.save(new VolunteerLog(volunteer, action, origin));
    }

    public void log(Volunteer volunteer, User changedBy, Actions action, ActionsOrigin origin) {
        volunteerLogRepository.save(new VolunteerLog(volunteer, changedBy, action, origin));
    }
}
