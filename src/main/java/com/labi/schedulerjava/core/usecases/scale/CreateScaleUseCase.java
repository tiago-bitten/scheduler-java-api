package com.labi.schedulerjava.core.usecases.scale;

import com.labi.schedulerjava.adapters.persistence.ScaleRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.*;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CreateScaleUseCase extends UseCase<CreateScaleUseCase.InputValues, CreateScaleUseCase.OutputValues> {

    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.getAuthHeader());
        Schedule schedule = scheduleService.findById(input.getScheduleId())
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.getScheduleId() + " não corresponde a uma agenda cadastrada"));

        Ministry ministry = ministryService.findById(input.getMinistryId())
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.getMinistryId() + " não corresponde a um ministério cadastrado"));

        return new OutputValues();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
        String authHeader;
        CreateScaleDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        //List<ReadSimpVolunteerDto> volunteers;
    }
}
