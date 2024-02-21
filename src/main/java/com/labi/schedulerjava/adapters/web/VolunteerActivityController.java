package com.labi.schedulerjava.adapters.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/volunteer-activities")
public class VolunteerActivityController {

    @PostMapping("/assign")
    public ResponseEntity<Void> assign(@RequestParam Long volunteerId, @RequestParam Long activityId) {
        return ResponseEntity.ok().build();
    }
}
