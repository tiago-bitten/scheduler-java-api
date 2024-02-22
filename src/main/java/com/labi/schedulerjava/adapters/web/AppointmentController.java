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
                                        @RequestParam Long ministryId,
                                        @RequestParam Long activityId,
                                        @RequestParam(required = false) Long totalVolunteers) {
        appointmentUseCase.execute(new CreateAppointmentUseCase.InputValues(scheduleId, volunteerId, ministryId,
                activityId, totalVolunteers, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        removeAppointmentUseCase.execute(new RemoveAppointmentUseCase.InputValues(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
