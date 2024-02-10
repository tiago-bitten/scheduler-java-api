package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.unavailabledate.CreateUnavailableDateUseCase;
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

    @PostMapping("/create")
    public ResponseEntity<Void> register(@RequestBody CreateUnavailableDateDto dto,
                                         @RequestParam Long volunteerId) {
        createUnavailableDateUseCase.execute(new CreateUnavailableDateUseCase.InputValues(dto, volunteerId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{volunteerId}")
    public ResponseEntity<UseCase.OutputValues> findAll(@PathVariable Long volunteerId) {
        UseCase.OutputValues outputValues =
                findUnavailableDatesByVolunteerUseCase.execute(new FindUnavailableDatesByVolunteerUseCase.InputValues(volunteerId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
