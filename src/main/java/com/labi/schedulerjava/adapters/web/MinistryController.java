package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.ministry.CreateMinistryUseCase;
import com.labi.schedulerjava.core.usecases.ministry.FindMinistriesUseCase;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
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

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateMinistryDto dto) {
        createMinistryUseCase.execute(new CreateMinistryUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<UseCase.OutputValues> findAll(@RequestParam(required = false) Long volunteerId) {
        UseCase.OutputValues outputValues =
                findMinistriesUseCase.execute(new FindMinistriesUseCase.InputValues(volunteerId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
