package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.group.CreateGroupUseCase;
import com.labi.schedulerjava.dtos.CreateGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    @Autowired
    private CreateGroupUseCase createGroupUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateGroupDto dto) {
        createGroupUseCase.execute(new CreateGroupUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //@PostMapping("/associate")
}
