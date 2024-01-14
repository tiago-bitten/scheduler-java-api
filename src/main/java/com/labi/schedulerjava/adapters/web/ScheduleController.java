package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.dtos.CreateScheduleDto;
import com.labi.schedulerjava.dtos.ReadScheduleDto;
import com.labi.schedulerjava.dtos.ReadSimpSchedule;
import com.labi.schedulerjava.service._ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/schedules")
public class ScheduleController {

    @Autowired
    private _ScheduleService scheduleService;

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

    @GetMapping("/appointments")
    public ResponseEntity<ReadScheduleDto> findScheduleAppointmens(@RequestParam Long scheduleId) {
        ReadScheduleDto schedule = scheduleService.findScheduleAppointments(scheduleId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReadSimpSchedule>> findSchedules(@RequestParam Integer month,
                                                                @RequestParam Integer year) {
        List<ReadSimpSchedule> schedules = scheduleService.findSchedules(month, year);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}
