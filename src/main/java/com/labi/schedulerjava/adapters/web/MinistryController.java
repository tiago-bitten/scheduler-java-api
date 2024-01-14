package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.ministry.CreateMinistryUseCase;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.service._MinistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/ministries")
public class MinistryController {

    @Autowired
    private _MinistryService ministryService;

    @Autowired
    private CreateMinistryUseCase createMinistryUseCase;

    @PostMapping("/create")
    public ResponseEntity<CreateMinistryUseCase.OutputValues> create(@RequestBody CreateMinistryDto dto) {
        createMinistryUseCase.execute(new CreateMinistryUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
