package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.userministry.AssociateUserMinistryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-ministries")
public class UserMinistryController {

    @Autowired
    private AssociateUserMinistryUseCase associateUserMinistryUseCase;

    @PostMapping("/associate")
    public ResponseEntity<UseCase.OutputValues> associate(@RequestHeader("Authorization") String authHeader,
                                                          @RequestParam Long ministryId) {
        associateUserMinistryUseCase.execute(new AssociateUserMinistryUseCase.InputValues(authHeader, ministryId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
