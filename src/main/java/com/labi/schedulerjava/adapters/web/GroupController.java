package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.group.AssociateVolunteerWithGroupUseCase;
import com.labi.schedulerjava.core.usecases.group.CreateGroupUseCase;
import com.labi.schedulerjava.core.usecases.group.FindGroupUseCase;
import com.labi.schedulerjava.dtos.AssociateVolunteerWithGroupDto;
import com.labi.schedulerjava.dtos.CreateGroupDto;
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
    private FindGroupUseCase findGroupUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateGroupDto dto) {
        createGroupUseCase.execute(new CreateGroupUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/associate")
    public ResponseEntity<Void> associate(@RequestBody AssociateVolunteerWithGroupDto dto,
                                          @RequestParam Long groupId) {
        associateVolunteerWithGroupUseCase.execute(new AssociateVolunteerWithGroupUseCase.InputValues(groupId, dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findGroup(@RequestParam Long groupId) {
        UseCase.OutputValues outputValues = findGroupUseCase.execute(new FindGroupUseCase.InputValues(groupId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
