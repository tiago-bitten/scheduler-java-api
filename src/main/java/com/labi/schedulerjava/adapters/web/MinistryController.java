package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.ministry.CreateMinistryUseCase;
import com.labi.schedulerjava.core.usecases.ministry.EditMinistryUseCase;
import com.labi.schedulerjava.core.usecases.ministry.FindMinistriesToSignUpUseCase;
import com.labi.schedulerjava.core.usecases.ministry.FindMinistriesUseCase;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.dtos.EditMinistryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ministries")
public class MinistryController {

    @Autowired
    private CreateMinistryUseCase createMinistryUseCase;

    @Autowired
    private FindMinistriesUseCase findMinistriesUseCase;

    @Autowired
    private FindMinistriesToSignUpUseCase findMinistriesToSignUpUseCase;

    @Autowired
    private EditMinistryUseCase editMinistryUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody CreateMinistryDto dto) {
        createMinistryUseCase.execute(new CreateMinistryUseCase.InputValues(dto, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findAll(@RequestParam(required = false) String ministryName,
                                                        @RequestParam(required = false) String volunteerName) {
        UseCase.OutputValues outputValues =
                findMinistriesUseCase.execute(new FindMinistriesUseCase.InputValues(ministryName, volunteerName));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @GetMapping("/signup")
    public ResponseEntity<UseCase.OutputValues> findAllToSignUp() {
        UseCase.OutputValues outputValues =
                findMinistriesToSignUpUseCase.execute(new FindMinistriesToSignUpUseCase.InputValues());
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> edit(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable Long id,
                                     @RequestBody EditMinistryDto dto) {
        editMinistryUseCase.execute(new EditMinistryUseCase.InputValues(dto, id, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
