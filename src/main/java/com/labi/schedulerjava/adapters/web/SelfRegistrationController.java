package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.selfregistration.CreateSelfRegistrationUseCase;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/self-registrations")
public class SelfRegistrationController {

    @Autowired
    private CreateSelfRegistrationUseCase createSelfRegistrationUseCase;

    @PostMapping("/create/{link}")
    public ResponseEntity<Void> create(@PathVariable UUID link,
                                       @RequestBody CreateVolunteerDto dto) {
        createSelfRegistrationUseCase.execute(new CreateSelfRegistrationUseCase.InputValues(link, dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
