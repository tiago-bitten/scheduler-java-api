package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.scale.CreateScaleUseCase;
import com.labi.schedulerjava.dtos.ScaleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scales")
public class ScaleController {

    @Autowired
    private CreateScaleUseCase createScaleUseCase;

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create(@RequestHeader("Authorization") String authHeader,
                                                       @RequestParam Long scheduleId,
                                                       @RequestParam Long ministryId,
                                                       @RequestBody ScaleRequest request) {
        UseCase.OutputValues outputValues =
            createScaleUseCase.execute(new CreateScaleUseCase.InputValues(authHeader, scheduleId, ministryId, request));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }
}
