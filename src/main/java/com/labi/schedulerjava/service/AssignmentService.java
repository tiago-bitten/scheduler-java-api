package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.*;
import com.labi.schedulerjava.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public void create(Schedule schedule, VolunteerMinistry volunteerMinistry) {
        Assignment assignment = new Assignment(schedule, volunteerMinistry);
        assignmentRepository.save(assignment);
    }

    public List<Assignment> findAllByScheduleId(Long scheduleId) {
        return assignmentRepository.findAll().stream()
                .filter(scheduleVolunteersMinistries -> scheduleVolunteersMinistries.getSchedule().getId().equals(scheduleId))
                .toList();
    }
}
