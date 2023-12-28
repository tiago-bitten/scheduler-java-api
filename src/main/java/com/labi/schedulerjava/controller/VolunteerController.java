package com.labi.schedulerjava.controller;

import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateVolunteerDto dto) {
        volunteerService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
