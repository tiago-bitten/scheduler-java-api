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

        List<Volunteer> allVolunteers = volunteerService.findAll(ministry.getId());
        List<Volunteer> availableVolunteers = filterAvailableVolunteers(allVolunteers, schedule, ministry.getId());

        List<Volunteer> selectedVolunteers = selectVolunteersConsideringGroups(availableVolunteers,
                input.getMaxVolunteers(), ministry.getId(), schedule);

        Scale scale = new Scale(input.maxVolunteers, schedule, ministry, user);
        scaleRepository.save(scale);

        return new OutputValues(selectedVolunteers.stream()
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
                .collect(Collectors.toList()));
    }

    private List<Volunteer> filterAvailableVolunteers(List<Volunteer> volunteers, Schedule schedule,
                                                      Long ministryId) {
        return volunteers.stream()
                .filter(volunteer -> volunteerMinistryService.validateVolunteerMinistry(volunteer.getId(),
                        ministryId).getIsActive())
                .filter(volunteer -> !unavailableDateService.isUnavailableDate(schedule.getStartDate(),
                        schedule.getEndDate(), volunteer.getId()))
                .collect(Collectors.toList());
    }

    private List<Volunteer> selectVolunteersConsideringGroups(List<Volunteer> availableVolunteers,
                                                              Long maxVolunteers, Long ministryId,
                                                              Schedule schedule) {
        List<Volunteer> selectedVolunteers = new ArrayList<>();
        Random random = new Random();
        Set<Long> processedGroups = new HashSet<>();

        availableVolunteers = availableVolunteers.stream()
                .filter(volunteer -> {
                    if (volunteer.getGroup() != null) {
                        if (processedGroups.contains(volunteer.getGroup().getId())) {
                            return false;
                        }
                        boolean allMembersEligible = volunteer.getGroup().getVolunteers().stream()
                                .allMatch(member -> volunteerMinistryService.isVolunteerInMinistry(member.getId(), ministryId) &&
                                        !unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), member.getId()));

                        if (!allMembersEligible) {
                            processedGroups.add(volunteer.getGroup().getId());
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        while (selectedVolunteers.size() < maxVolunteers && !availableVolunteers.isEmpty()) {
            int index = random.nextInt(availableVolunteers.size());
            Volunteer randomVolunteer = availableVolunteers.get(index);

            if (randomVolunteer.getGroup() != null && !processedGroups.contains(randomVolunteer.getGroup().getId())) {
                List<Volunteer> groupMembers = new ArrayList<>(randomVolunteer.getGroup().getVolunteers());
                if ((selectedVolunteers.size() + groupMembers.size()) <= maxVolunteers) {
                    selectedVolunteers.addAll(groupMembers);
                    processedGroups.add(randomVolunteer.getGroup().getId());
                }
                availableVolunteers.removeAll(groupMembers);
            } else if (randomVolunteer.getGroup() == null) {
                selectedVolunteers.add(randomVolunteer);
                availableVolunteers.remove(randomVolunteer);
            }
        }

        return selectedVolunteers;
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
        List<ReadSimpVolunteerDto> volunteers;
    }
}
