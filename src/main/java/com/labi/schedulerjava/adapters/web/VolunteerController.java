package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.volunteer.CreateVolunteerUseCase;
import com.labi.schedulerjava.core.usecases.volunteer.FindVolunteersUseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
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

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateVolunteerDto dto) {
        createVolunteerUseCase.execute(new CreateVolunteerUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findAll(@RequestParam(required = false) Long ministryId) {
        UseCase.OutputValues outputValues =
                findVolunteersUseCase.execute(new FindVolunteersUseCase.InputValues(ministryId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
