package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.*;
import com.labi.schedulerjava.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private ScheduleService scheduleService;

    public void assignVolunteerToSchedule(Long scheduleId, Long volunteerId, Long ministryId) {
        Schedule schedule = scheduleService.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("ScheduleGrid not found"));
        VolunteerMinistry volunteerMinistry = volunteerMinistryService.findByVolunteerAndMinistry(volunteerId, ministryId)
                .orElseThrow(() -> new RuntimeException("Volunteer Ministry not found"));

        Optional<Assignment> existsAssignment = schedule.getAssignments().stream()
                .filter(assignment -> assignment.getVolunteerMinistry().getVolunteer().getId().equals(volunteerId))
                .findFirst();

        if (existsAssignment.isPresent())
            throw new RuntimeException("Volunteer already assigned to this schedule");

        Assignment assignment = new Assignment(schedule, volunteerMinistry);
        assignmentRepository.save(assignment);
    }
}
