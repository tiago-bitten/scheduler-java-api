package com.labi.schedulerjava.controller;

import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.service.MinistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ministries")
public class MinistryController {

    @Autowired
    private MinistryService ministryService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateMinistryDto dto) {
        ministryService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
