package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.unavailabledate.CreateUnavailableDateByAccessKeyUseCase;
import com.labi.schedulerjava.core.usecases.unavailabledate.CreateUnavailableDateUseCase;
import com.labi.schedulerjava.core.usecases.unavailabledate.FindUnavailableDatesByAccessKeyUseCase;
import com.labi.schedulerjava.core.usecases.unavailabledate.FindUnavailableDatesByVolunteerUseCase;
import com.labi.schedulerjava.dtos.CreateUnavailableDateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/unavailable-dates")
public class UnavailableDateController {

    @Autowired
    CreateUnavailableDateUseCase createUnavailableDateUseCase;

    @Autowired
    private FindUnavailableDatesByVolunteerUseCase findUnavailableDatesByVolunteerUseCase;

    @Autowired
    private CreateUnavailableDateByAccessKeyUseCase createUnavailableDateByAccessKeyUseCase;

    @Autowired
    private FindUnavailableDatesByAccessKeyUseCase findUnavailableDatesByAccessKeyUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> register(@RequestBody CreateUnavailableDateDto dto,
                                         @RequestParam Long volunteerId) {
        createUnavailableDateUseCase.execute(new CreateUnavailableDateUseCase.InputValues(dto, volunteerId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create/{accessKey}")
    public ResponseEntity<Void> register(@RequestBody CreateUnavailableDateDto dto,
                                         @PathVariable String accessKey) {
        createUnavailableDateByAccessKeyUseCase.execute(new CreateUnavailableDateByAccessKeyUseCase.InputValues(dto, accessKey));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{volunteerId}")
    public ResponseEntity<UseCase.OutputValues> findAll(@PathVariable Long volunteerId) {
        UseCase.OutputValues outputValues =
                findUnavailableDatesByVolunteerUseCase.execute(new FindUnavailableDatesByVolunteerUseCase.InputValues(volunteerId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/{accessKey}")
    public ResponseEntity<UseCase.OutputValues> findAll(@PathVariable String accessKey) {
        UseCase.OutputValues outputValues =
                findUnavailableDatesByAccessKeyUseCase.execute(new FindUnavailableDatesByAccessKeyUseCase.InputValues(accessKey));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
