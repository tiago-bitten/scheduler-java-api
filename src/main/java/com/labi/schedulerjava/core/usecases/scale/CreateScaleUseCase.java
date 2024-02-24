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

    @Autowired
    private ScaleService scaleService;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private ActivityService activityService;

    @Override
    public OutputValues execute(InputValues input) {
        User user = jwtTokenProvider.getUserFromToken(input.getAuthHeader());

        Schedule schedule = scheduleService.findById(input.getScheduleId())
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.getScheduleId() + " não corresponde a um horário cadastrado"));

        Ministry ministry = ministryService.findById(input.getMinistryId())
                .orElseThrow(() -> new BusinessRuleException("O ID " + input.getMinistryId() + " não corresponde a um ministério cadastrado"));

        input.dto.activityIdVolunteers().forEach((activityId, volunteers) -> {
            if (!activityService.validateActivities(activityId, ministry.getId()))
                throw new BusinessRuleException("O ID " + activityId + " não corresponde a uma atividade cadastrada neste ministério");
        });

        if (!userMinistryService.existsUserMinistryRelation(user.getId(), ministry.getId()))
            throw new BusinessRuleException("Você não tem permissão para criar escalas neste ministério");

        if (!scaleService.checkVolunteersSize(input.dto.activityIdVolunteers()))
            throw new BusinessRuleException("Informe no minimo um voluntário para cada atividade");

        List<ScaleResponse> scales = new ArrayList<>();

        input.dto.activityIdVolunteers().forEach((activityId, numberOfVolunteers) -> {
            Activity activity = activityService.findById(activityId).get();
            List<Volunteer> volunteers = scaleService.createIndividualScale(ministry, schedule, numberOfVolunteers);

            Scale scale = new Scale(numberOfVolunteers, schedule, ministry, activity, user);
            scaleRepository.save(scale);

            scales.addAll(volunteers.stream()
                    .map(volunteer -> toDto(volunteer, ministry, activity, numberOfVolunteers, scale))
                    .toList());
        });

        return new OutputValues(scales);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        String authHeader;
        Long scheduleId;
        Long ministryId;
        CreateScaleDto dto;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ScaleResponse> scales;
    }

    private ScaleResponse toDto(Volunteer volunteer, Ministry ministry, Activity activity,
                                Long numberOfVolunteers, Scale scale) {
        return new ScaleResponse(
                scale.getId(),
                numberOfVolunteers,
                new ReadSimpVolunteerDto(
                        volunteer.getId(),
                        volunteer.getAccessKey(),
                        volunteer.getName(),
                        volunteer.getLastName(),
                        volunteer.getCpf(),
                        volunteer.getPhone(),
                        volunteer.getBirthDate(),
                        volunteer.getOrigin()
                ),
                new ReadMinistryDto(
                        ministry.getId(),
                        ministry.getName(),
                        ministry.getDescription(),
                        ministry.getColor(),
                        ministry.getTotalVolunteers()
                ),
                new ActivityResponse(
                        activity.getId(),
                        activity.getName(),
                        activity.getDefaultTotalVolunteers()
                )
        );
    }
}
