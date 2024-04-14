package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.schedule.*;
import com.labi.schedulerjava.dtos.CreateScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/schedules")
public class ScheduleController {

    @Autowired
    private OpenScheduleUseCase openScheduleUseCase;

    @Autowired
    private CloseScheduleUseCase closeScheduleUseCase;

    @Autowired
    private FindScheduleAppointmentsUseCase findScheduleAppointmentsUseCase;

    @Autowired
    private FindSchedulesUseCase findSchedulesUseCase;

    @Autowired
    private ReOpenScheduleUseCase reOpenScheduleUseCase;

    @Autowired
    private RemoveScheduleUseCase removeScheduleUseCase;

    @PostMapping("/open")
    public ResponseEntity<Void> open(@RequestHeader("Authorization") String authHeader,
                                     @RequestBody CreateScheduleDto dto) {
        openScheduleUseCase.execute(new OpenScheduleUseCase.InputValues(authHeader, dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/close")
    public ResponseEntity<Void> close(@RequestParam Long scheduleId) {
        closeScheduleUseCase.execute(new CloseScheduleUseCase.InputValues(scheduleId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/reopen")
    public ResponseEntity<Void> reopen(@RequestParam Long scheduleId) {
        reOpenScheduleUseCase.execute(new ReOpenScheduleUseCase.InputValues(scheduleId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/appointments")
    public ResponseEntity<UseCase.OutputValues> findScheduleAppointments(@RequestParam Long scheduleId,
                                                                         @RequestParam(required = false) String volunteerName) {
        UseCase.OutputValues outputValues =
                findScheduleAppointmentsUseCase.execute(new FindScheduleAppointmentsUseCase.InputValues(scheduleId, volunteerName));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findSchedules(@RequestParam Integer month,
                                                              @RequestParam Integer year) {
        UseCase.OutputValues outputValues =
                findSchedulesUseCase.execute(new FindSchedulesUseCase.InputValues(month, year));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> remove(@RequestParam Long scheduleId) {
        removeScheduleUseCase.execute(new RemoveScheduleUseCase.InputValues(scheduleId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
