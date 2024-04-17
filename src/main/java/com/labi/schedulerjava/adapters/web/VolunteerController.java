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

    @Autowired
    private DeleteVolunteerUseCase deleteVolunteerUseCase;

    @Autowired
    private FindVolunteersNotInGroupUseCase findVolunteersNotInGroupUseCase;

    @Autowired
    private FindVolunteersByGroupUseCase findVolunteersByGroupUseCase;

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create(@RequestBody @Valid CreateVolunteerDto dto,
                                                       @RequestHeader("Authorization") String authHeader){
        UseCase.OutputValues outputValues =
                createVolunteerUseCase.execute(new CreateVolunteerUseCase.InputValues(dto, authHeader));
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
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findAll(@RequestParam(required = false) String volunteerName,
                                                        @RequestParam(required = false) String ministryName,
                                                        @RequestParam(defaultValue = "false") Boolean isLinkedToAnyMinistry,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        UseCase.OutputValues outputValues =
                findVolunteersUseCase.execute(new FindVolunteersUseCase.InputValues(volunteerName, ministryName, isLinkedToAnyMinistry, page, size));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/not-in-ministry/{ministryId}")
    public ResponseEntity<UseCase.OutputValues> findVolunteersNotInMinistry(@PathVariable Long ministryId,
                                                                            @RequestParam(required = false) String volunteerName) {
        UseCase.OutputValues outputValues =
                findVolunteersNotIntMinistryUseCase.execute(new FindVolunteersNotIntMinistryUseCase.InputValues(ministryId, volunteerName));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/not-in-schedule/{scheduleId}/ministry/{ministryId}")
    public ResponseEntity<UseCase.OutputValues> findVolunteersNotInSchedule(@PathVariable Long scheduleId,
                                                                            @PathVariable Long ministryId,
                                                                            @RequestParam(required = false) String volunteerName) {
        UseCase.OutputValues outputValues =
                findVolunteersNotInScheduleUseCase.execute(new FindVolunteersNotInScheduleUseCase.InputValues(scheduleId, ministryId, volunteerName));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
                deleteVolunteerUseCase.execute(new DeleteVolunteerUseCase.InputValues(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/not-in-group")
    public ResponseEntity<UseCase.OutputValues> findVolunteersNotInGroup() {
        UseCase.OutputValues outputValues =
                findVolunteersNotInGroupUseCase.execute(new FindVolunteersNotInGroupUseCase.InputValues());
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<UseCase.OutputValues> findVolunteersNotInGroup(@PathVariable Long groupId) {
        UseCase.OutputValues outputValues =
                findVolunteersByGroupUseCase.execute(new FindVolunteersByGroupUseCase.InputValues(groupId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
