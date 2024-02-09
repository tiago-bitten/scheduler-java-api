package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.volunteer.*;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.dtos.SignInVolunteerDto;
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

    @Autowired
    private SignInVolunteerUseCase signInVolunteerUseCase;

    @Autowired
    private AutoCreateVolunteerUseCase autoCreateVolunteerUseCase;

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create(@RequestBody @Valid CreateVolunteerDto dto) {
        UseCase.OutputValues outputValues =
                createVolunteerUseCase.execute(new CreateVolunteerUseCase.InputValues(dto));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @PostMapping("/auto-create")
    public ResponseEntity<UseCase.OutputValues> autoCreate(@RequestBody @Valid CreateVolunteerDto dto) {
        UseCase.OutputValues outputValues =
                autoCreateVolunteerUseCase.execute(new AutoCreateVolunteerUseCase.InputValues(dto));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UseCase.OutputValues> signIn(@RequestBody @Valid SignInVolunteerDto dto) {
        UseCase.OutputValues outputValues =
                signInVolunteerUseCase.execute(new SignInVolunteerUseCase.InputValues(dto));
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
