package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.volunteer.CreateVolunteerUseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import com.labi.schedulerjava.service._VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/volunteers")
public class VolunteerController {

    @Autowired
    private _VolunteerService volunteerService;

    @Autowired
    private CreateVolunteerUseCase createVolunteerUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateVolunteerDto dto) {
        createVolunteerUseCase.execute(new CreateVolunteerUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/associate")
    public ResponseEntity<Void> addMinistry(@RequestParam Long volunteerId,
                                            @RequestParam Long ministryId) {
        volunteerService.addMinistry(volunteerId, ministryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ReadVolunteerDto>> findAll(@RequestParam(required = false) Long ministryId) {
        return new ResponseEntity<>(volunteerService.findAll(ministryId), HttpStatus.OK);
    }
}
