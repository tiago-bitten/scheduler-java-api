package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.schedule.CloseScheduleUseCase;
import com.labi.schedulerjava.core.usecases.schedule.FindScheduleAppointmentsUseCase;
import com.labi.schedulerjava.core.usecases.schedule.FindSchedulesUseCase;
import com.labi.schedulerjava.core.usecases.schedule.OpenScheduleUseCase;
import com.labi.schedulerjava.dtos.CreateScheduleDto;
import com.labi.schedulerjava.dtos.ReadSimpScheduleDto;
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

    @Autowired
    private OpenScheduleUseCase openScheduleUseCase;

    @Autowired
    private CloseScheduleUseCase closeScheduleUseCase;

    @Autowired
    private FindScheduleAppointmentsUseCase findScheduleAppointmentsUseCase;

    @Autowired
    private FindSchedulesUseCase findSchedulesUseCase;

    @PostMapping("/open")
    public ResponseEntity<Void> open(@RequestBody CreateScheduleDto dto) {
        openScheduleUseCase.execute(new OpenScheduleUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/close")
    public ResponseEntity<Void> close(@RequestParam Long scheduleId) {
        closeScheduleUseCase.execute(new CloseScheduleUseCase.InputValues(scheduleId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/appointments")
    public ResponseEntity<UseCase.OutputValues> findScheduleAppointmens(@RequestParam Long scheduleId) {
        UseCase.OutputValues outputValues =
                findScheduleAppointmentsUseCase.execute(new FindScheduleAppointmentsUseCase.InputValues(scheduleId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findSchedules(@RequestParam Integer month,
                                                              @RequestParam Integer year) {
        UseCase.OutputValues outputValues =
                findSchedulesUseCase.execute(new FindSchedulesUseCase.InputValues(month, year));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
