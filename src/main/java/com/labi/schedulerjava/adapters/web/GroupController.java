package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.group.*;
import com.labi.schedulerjava.dtos.AssociateVolunteerWithGroupDto;
import com.labi.schedulerjava.dtos.CreateGroupDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @Autowired
    private CreateGroupUseCase createGroupUseCase;

    @Autowired
    private AssociateVolunteerWithGroupUseCase associateVolunteerWithGroupUseCase;

    @Autowired
    private DisassociateVolunteerGroupUseCase disassociateVolunteerGroupUseCase;

    @Autowired
    private FindGroupUseCase findGroupUseCase;

    @Autowired
    private FindGroupByNameToAppointUseCase findGroupByNameToAppointUseCase;

    @Autowired
    private FindAllGroupUseCase findAllGroupUseCase;

    @Autowired
    private DeleteGroupUseCase deleteGroupUseCase;

    @Autowired
    private FindGroupsNotInScheduleUseCase findGroupsNotInScheduleUseCase;

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create(@RequestBody @Valid CreateGroupDto dto) {
        UseCase.OutputValues outputValues =
                createGroupUseCase.execute(new CreateGroupUseCase.InputValues(dto));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/associate")
    public ResponseEntity<Void> associate(@PathVariable Long groupId,
                                          @RequestParam Long volunteerId) {
        associateVolunteerWithGroupUseCase.execute(new AssociateVolunteerWithGroupUseCase.InputValues(groupId, volunteerId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findAll(@RequestParam(required = false) String groupName,
                                                        @RequestParam(required = false) String volunteerName) {
        UseCase.OutputValues outputValues =
                findAllGroupUseCase.execute(new FindAllGroupUseCase.InputValues(groupName, volunteerName));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UseCase.OutputValues> findGroup(@PathVariable Long id) {
        UseCase.OutputValues outputValues =
                findGroupUseCase.execute(new FindGroupUseCase.InputValues(id));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<UseCase.OutputValues> findGroupByName(@PathVariable String name,
                                                                @RequestParam Long ministryId,
                                                                @RequestParam Long scheduleId) {
        UseCase.OutputValues outputValues =
                findGroupByNameToAppointUseCase.execute(new FindGroupByNameToAppointUseCase.InputValues(name, ministryId, scheduleId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/ministry/{ministryId}/schedule/{scheduleId}")
    public ResponseEntity<UseCase.OutputValues> findGroupsNotInSchedule(@PathVariable Long ministryId,
                                                                        @PathVariable Long scheduleId,
                                                                        @RequestParam(required = false) String groupName) {
        UseCase.OutputValues outputValues =
                findGroupsNotInScheduleUseCase.execute(new FindGroupsNotInScheduleUseCase.InputValues(ministryId, scheduleId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @PutMapping("/{groupId}/disassociate")
    public ResponseEntity<Void> disassociate(@PathVariable Long groupId,
                                             @RequestParam Long volunteerId) {
        disassociateVolunteerGroupUseCase.execute(new DisassociateVolunteerGroupUseCase.InputValues(groupId, volunteerId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteGroupUseCase.execute(new DeleteGroupUseCase.InputValues(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
