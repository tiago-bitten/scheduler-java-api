package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.schedule.CloseScheduleUseCase;
import com.labi.schedulerjava.core.usecases.schedule.FindScheduleAppointmentsUseCase;
import com.labi.schedulerjava.core.usecases.schedule.OpenScheduleUseCase;
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

    @Autowired
    private OpenScheduleUseCase openScheduleUseCase;

    @Autowired
    private CloseScheduleUseCase closeScheduleUseCase;

    @Autowired
    private FindScheduleAppointmentsUseCase findScheduleAppointmentsUseCase;

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
    public ResponseEntity<FindScheduleAppointmentsUseCase.OutputValues> findScheduleAppointmens(@RequestParam Long scheduleId) {
        FindScheduleAppointmentsUseCase.OutputValues outputValues =
                findScheduleAppointmentsUseCase.execute(new FindScheduleAppointmentsUseCase.InputValues(scheduleId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReadSimpSchedule>> findSchedules(@RequestParam Integer month,
                                                                @RequestParam Integer year) {
        List<ReadSimpSchedule> schedules = scheduleService.findSchedules(month, year);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}
