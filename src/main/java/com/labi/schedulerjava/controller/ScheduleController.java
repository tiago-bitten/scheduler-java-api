package com.labi.schedulerjava.controller;

import com.labi.schedulerjava.dtos.CreateScheduleDto;
import com.labi.schedulerjava.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/open")
    public ResponseEntity<Void> open(@RequestBody CreateScheduleDto dto) {
        scheduleService.open(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/close")
    public ResponseEntity<Void> close(@RequestParam Long scheduleId) {
        scheduleService.close(scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addVolunteer")
    public ResponseEntity<Void> addVolunteer(@RequestParam Long scheduleId,
                                             @RequestParam Long volunteerMinistryId) {
        scheduleService.addVolunteer(scheduleId, volunteerMinistryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
