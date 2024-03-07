package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.activity.CreateActivityUseCase;
import com.labi.schedulerjava.core.usecases.activity.FindAllActivitiesByMinistry;
import com.labi.schedulerjava.core.usecases.activity.UpdateActivityUseCase;
import com.labi.schedulerjava.dtos.ActivityRequest;
import com.labi.schedulerjava.dtos.UpdateActivityRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {

    @Autowired
    private CreateActivityUseCase createActivityUseCase;

    @Autowired
    private FindAllActivitiesByMinistry findAllActivitiesByMinistry;

    @Autowired
    private UpdateActivityUseCase updateActivityUseCase;

    @PostMapping("/create")
    public ResponseEntity<UseCase.OutputValues> create(@RequestBody @Valid ActivityRequest request,
                                                       @RequestParam Long ministryId) {
        UseCase.OutputValues outputValues =
                createActivityUseCase.execute(new CreateActivityUseCase.InputValues(ministryId, request));
        return new ResponseEntity<>(outputValues, HttpStatus.CREATED);
    }

    @GetMapping("/ministry/{ministryId}")
    public ResponseEntity<UseCase.OutputValues> findAll(@PathVariable Long ministryId) {
        UseCase.OutputValues outputValues =
                findAllActivitiesByMinistry.execute(new FindAllActivitiesByMinistry.InputValues(ministryId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UseCase.OutputValues> update(@RequestHeader("Authorization") String authHeader,
                                                       @PathVariable Long id,
                                                       @RequestBody @Valid UpdateActivityRequest request) {
        UseCase.OutputValues outputValues =
                updateActivityUseCase.execute(new UpdateActivityUseCase.InputValues(authHeader, id, request));

        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
