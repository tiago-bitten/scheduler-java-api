package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.appointment.CreateAppointmentUseCase;
import com.labi.schedulerjava.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase appointmentUseCase;

    @PostMapping("/appoint")
    public ResponseEntity<Void> appoint(@RequestParam Long scheduleId,
                                        @RequestParam Long volunteerId,
                                        @RequestParam Long ministryId,
                                        @RequestParam Long userId) {
        appointmentUseCase.create(scheduleId, volunteerId, ministryId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
