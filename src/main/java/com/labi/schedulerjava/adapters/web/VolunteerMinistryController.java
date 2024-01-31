package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.volunteerministry.AssociateVolunteerMinistryUseCase;
import com.labi.schedulerjava.core.usecases.volunteerministry.DisassociateVolunteerMinistryUseCase;
import com.labi.schedulerjava.core.usecases.volunteerministry.FindVolunteerMinistryByMinistryIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/volunteer-ministries")
public class VolunteerMinistryController {

    @Autowired
    private AssociateVolunteerMinistryUseCase associateVolunteerMinistryUseCase;

    @Autowired
    private DisassociateVolunteerMinistryUseCase disassociateVolunteerMinistryUseCase;

    @Autowired
    private FindVolunteerMinistryByMinistryIdUseCase findVolunteerMinistryByMinistryIdUseCase;

    @PostMapping("/associate")
    public ResponseEntity<Void> associate(@RequestHeader("Authorization") String authHeader,
                                          @RequestParam Long volunteerId,
                                          @RequestParam Long ministryId) {
        associateVolunteerMinistryUseCase.execute(new AssociateVolunteerMinistryUseCase.InputValues(volunteerId, ministryId, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/disassociate")
    public ResponseEntity<Void> disassociate(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam Long volunteerId,
                                             @RequestParam Long ministryId) {
        disassociateVolunteerMinistryUseCase.execute(new DisassociateVolunteerMinistryUseCase.InputValues(volunteerId, ministryId, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ministry/{ministryId}")
    public ResponseEntity<UseCase.OutputValues> findVolunteerMinistriesByMinistryId(@PathVariable Long ministryId) {
        UseCase.OutputValues outputValues =
                findVolunteerMinistryByMinistryIdUseCase.execute(new FindVolunteerMinistryByMinistryIdUseCase.InputValues(ministryId));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}
