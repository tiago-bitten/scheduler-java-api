package com.labi.schedulerjava.adapters.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/volunteer-ministries")
public class VolunteerMinistryController {

    public ResponseEntity<Void> associate(@RequestParam Long volunteerId,
                                           @RequestParam Long ministryId) {
        return null;
    }
}
