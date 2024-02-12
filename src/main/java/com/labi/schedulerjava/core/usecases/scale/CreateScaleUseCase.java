package com.labi.schedulerjava.core.usecases.scale;

import com.labi.schedulerjava.adapters.persistence.ScaleRepository;
import com.labi.schedulerjava.adapters.security.JwtTokenProvider;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import com.labi.schedulerjava.core.domain.service.*;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadScaleDto;
import com.labi.schedulerjava.dtos.ReadSimpScheduleDto;
import com.labi.schedulerjava.dtos.ReadSimpVolunteerDto;
import com.labi.schedulerjava.dtos.ReadVolunteerMinistry;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        List<Volunteer> volunteers = volunteerService.findAll(ministry.getId());
        List<Volunteer> availableVolunteers = new ArrayList<>();
        for (Volunteer volunteer : volunteers) {
            if (volunteerMinistryService.validateVolunteerMinistry(volunteer.getId(), ministry.getId()).getIsActive()) {
                if (!unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId())) {
                    availableVolunteers.add(volunteer);
                }
            }
        }

        availableVolunteers.forEach(volunteer -> {
            if (volunteer.getGroup() != null) {
                volunteer.getGroup().getVolunteers().forEach(groupVolunteer -> {
                    if (!unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), groupVolunteer.getId())) {
                        availableVolunteers.add(groupVolunteer);
                    } else {
                        if (availableVolunteers.contains(groupVolunteer)) {
                            availableVolunteers.remove(groupVolunteer);
                        }
                        availableVolunteers.remove(volunteer);
                    }
                });
            }
        });

        Random random = new Random();

        List<Volunteer> selectedVolunteers = new ArrayList<>();
        for (int i = 0; i < input.getMaxVolunteers() && i < availableVolunteers.size(); i++) {
            Volunteer volunteer = availableVolunteers.get(random.nextInt(availableVolunteers.size()));
            if (!selectedVolunteers.contains(volunteer)) {
                selectedVolunteers.add(volunteer);
            }
        }

        Scale scale = new Scale(input.maxVolunteers, schedule, user);
        scaleRepository.save(scale);

        return new OutputValues(new ReadScaleDto(
                scale.getId(),
                scale.getMaxVolunteers(),
                new ReadSimpScheduleDto(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getStartDate(),
                        schedule.getEndDate(),
                        schedule.getWeekNumber(),
                        schedule.getIsActive()
                ),
                selectedVolunteers.stream()
                        .map(volunteer -> new ReadSimpVolunteerDto(
                                volunteer.getId(),
                                volunteer.getAccessKey(),
                                volunteer.getName(),
                                volunteer.getLastName(),
                                volunteer.getCpf(),
                                volunteer.getPhone(),
                                volunteer.getBirthDate(),
                                volunteer.getOrigin()
                        ))
                        .toList()
        ));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long scheduleId;
        Long ministryId;
        Long maxVolunteers;
        String authHeader;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        ReadScaleDto scale;
    }
}
