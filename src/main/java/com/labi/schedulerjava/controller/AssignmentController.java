package com.labi.schedulerjava.controller;

import com.labi.schedulerjava.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/assign")
    public ResponseEntity<Void> addVolunteer(@RequestParam Long scheduleId,
                                             @RequestParam Long volunteerId,
                                             @RequestParam Long ministryId) {
        assignmentService.assignVolunteerToSchedule(scheduleId, volunteerId, ministryId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
