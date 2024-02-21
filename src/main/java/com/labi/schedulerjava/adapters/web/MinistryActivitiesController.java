package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ministry-activities")
public class MinistryActivitiesController {

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
