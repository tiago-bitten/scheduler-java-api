package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.appointment.CreateAppointmentUseCase;
import com.labi.schedulerjava.core.usecases.appointment.RemoveAppointmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase appointmentUseCase;

    @Autowired
    private RemoveAppointmentUseCase removeAppointmentUseCase;

    @PostMapping("/appoint")
    public ResponseEntity<Void> appoint(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam Long scheduleId,
                                        @RequestParam Long volunteerId,
                                        @RequestParam Long ministryId) {
        appointmentUseCase.execute(new CreateAppointmentUseCase.InputValues(scheduleId, volunteerId, ministryId, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> remove(@RequestParam Long appointmentId) {
        removeAppointmentUseCase.execute(new RemoveAppointmentUseCase.InputValues(appointmentId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
