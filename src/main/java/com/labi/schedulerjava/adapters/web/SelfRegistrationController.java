package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.selfregistration.CreateLinkUseCase;
import com.labi.schedulerjava.core.usecases.selfregistration.CreateSelfRegistrationUseCase;
import com.labi.schedulerjava.core.usecases.selfregistration.ValidateLinkUseCase;
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

    @Autowired
    private CreateLinkUseCase createLinkUseCase;

    @Autowired
    private ValidateLinkUseCase validateLinkUseCase;

    @PostMapping("/create/{link}")
    public ResponseEntity<UseCase.OutputValues> create(@PathVariable UUID link,
                                                       @RequestBody CreateVolunteerDto dto) {
        UseCase.OutputValues outputValues =
            createSelfRegistrationUseCase.execute(new CreateSelfRegistrationUseCase.InputValues(link, dto));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @PostMapping("/generate/link")
    public ResponseEntity<UseCase.OutputValues> createLink() {
        UseCase.OutputValues outputValues =
            createLinkUseCase.execute(new CreateLinkUseCase.InputValues());
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @GetMapping("/validate/{link}")
    public ResponseEntity<Void> validate(@PathVariable UUID link) {
        validateLinkUseCase.execute(new ValidateLinkUseCase.InputValues(link));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
