package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.appointment.CreateAppointmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase appointmentUseCase;

    @PostMapping("/appoint")
    public ResponseEntity<Void> appoint(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam Long scheduleId,
                                        @RequestParam Long volunteerId,
                                        @RequestParam Long ministryId) {
        appointmentUseCase.execute(new CreateAppointmentUseCase.InputValues(scheduleId, volunteerId, ministryId, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
