package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.volunteerministry.AssociateVolunteerMinistryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/volunteer-ministries")
public class VolunteerMinistryController {

    @Autowired
    private AssociateVolunteerMinistryUseCase associateVolunteerMinistryUseCase;

    @PostMapping("/associate")
    public ResponseEntity<Void> associate(@RequestParam Long volunteerId,
                                          @RequestParam Long ministryId) {
        associateVolunteerMinistryUseCase.execute(new AssociateVolunteerMinistryUseCase.InputValues(volunteerId, ministryId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
