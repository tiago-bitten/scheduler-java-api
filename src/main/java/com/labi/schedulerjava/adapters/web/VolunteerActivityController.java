package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.volunteeractivity.AssignVolunteerActivityUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/volunteer-activities")
public class VolunteerActivityController {

    @Autowired
    private AssignVolunteerActivityUseCase assignVolunteerActivityUseCase;

    @PostMapping("/assign")
    public ResponseEntity<Void> assign(@RequestParam Long volunteerId, @RequestParam Long activityId) {
        assignVolunteerActivityUseCase.execute(new AssignVolunteerActivityUseCase.InputValues(volunteerId, activityId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
