package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.volunteer.CreateVolunteerUseCase;
import com.labi.schedulerjava.core.usecases.volunteer.FindVolunteersNotInScheduleUseCase;
import com.labi.schedulerjava.core.usecases.volunteer.FindVolunteersNotIntMinistryUseCase;
import com.labi.schedulerjava.core.usecases.volunteer.FindVolunteersUseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/volunteers")
public class VolunteerController {

    @Autowired
    private CreateVolunteerUseCase createVolunteerUseCase;

    @Autowired
    private FindVolunteersUseCase findVolunteersUseCase;

    @Autowired
    private FindVolunteersNotIntMinistryUseCase findVolunteersNotIntMinistryUseCase;

    @Autowired
    private FindVolunteersNotInScheduleUseCase findVolunteersNotInScheduleUseCase;

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create(@RequestBody @Valid CreateVolunteerDto dto) {
        UseCase.OutputValues outputValues =
                createVolunteerUseCase.execute(new CreateVolunteerUseCase.InputValues(dto));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findAll(@RequestParam(required = false) Long ministryId) {
        UseCase.OutputValues outputValues =
                findVolunteersUseCase.execute(new FindVolunteersUseCase.InputValues(ministryId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/not-in-ministry/{ministryId}")
    public ResponseEntity<UseCase.OutputValues> findVolunteersNotInMinistry(@PathVariable Long ministryId) {
        UseCase.OutputValues outputValues =
                findVolunteersNotIntMinistryUseCase.execute(new FindVolunteersNotIntMinistryUseCase.InputValues(ministryId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/not-in-schedule/{scheduleId}/ministry/{ministryId}")
    public ResponseEntity<UseCase.OutputValues> findVolunteersNotInSchedule(@PathVariable Long scheduleId,
                                                                            @PathVariable Long ministryId) {
        UseCase.OutputValues outputValues =
                findVolunteersNotInScheduleUseCase.execute(new FindVolunteersNotInScheduleUseCase.InputValues(scheduleId, ministryId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
