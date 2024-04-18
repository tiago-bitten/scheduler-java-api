package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.appointment.CreateAppointmentUseCase;
import com.labi.schedulerjava.core.usecases.appointment.CreateGroupAppointmentUseCase;
import com.labi.schedulerjava.core.usecases.appointment.RemoveAppointmentUseCase;
import com.labi.schedulerjava.dtos.CreateGroupAppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    @Autowired
    private CreateAppointmentUseCase appointmentUseCase;

    @Autowired
    private RemoveAppointmentUseCase removeAppointmentUseCase;

    @Autowired
    private CreateGroupAppointmentUseCase createGroupAppointmentUseCase;

    @PostMapping("/appoint")
    public ResponseEntity<Void> appoint(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam Long scheduleId,
                                        @RequestParam Long volunteerId,
                                        @RequestParam Long ministryId,
                                        @RequestParam Long activityId) {
        appointmentUseCase.execute(new CreateAppointmentUseCase.InputValues(scheduleId, volunteerId, ministryId,
                activityId, authHeader));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/appoint-group")
    public ResponseEntity<Void> appointGroup(@RequestHeader("Authorization") String authHeader,
                                             @RequestParam Long scheduleId,
                                             @RequestParam Long ministryId,
                                             @RequestBody List<CreateGroupAppointmentDto> dto) {
        createGroupAppointmentUseCase.execute(new CreateGroupAppointmentUseCase.InputValues(authHeader, scheduleId, ministryId, dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        removeAppointmentUseCase.execute(new RemoveAppointmentUseCase.InputValues(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
