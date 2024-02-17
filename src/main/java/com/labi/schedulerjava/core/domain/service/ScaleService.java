package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.ScaleRepository;
import com.labi.schedulerjava.adapters.persistence.specification.VolunteerSpecification;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScaleService {

    @Autowired
    private ScaleRepository scaleRepository;

    @Autowired
    private MinistryService ministryService;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private UnavailableDateService unavailableDateService;

    public List<Ministry> validateMinistries(Map<Long, Long> ministriesIdMaxVolunteers) {
        List<Ministry> ministries = new ArrayList<>();
        ministriesIdMaxVolunteers.forEach((id, max) -> {
            Ministry ministry = ministryService.findById(id)
                    .orElseThrow(() -> new BusinessRuleException("O ID " + id + " não corresponde a um ministério cadastrado"));
            ministries.add(ministry);
        });

        if (ministries.isEmpty())
            throw new BusinessRuleException("Nenhum ministério foi encontrado");

        return ministries;
    }

    public boolean validateMaxVolunteersSize(Map<Long, Long> ministriesIdMaxVolunteers) {
        return ministriesIdMaxVolunteers.values().stream().allMatch(max -> max > 0);
    }

    public List<Volunteer> createIndividualScale(Ministry ministry, Schedule schedule, Long maxVolunteers) {
        Specification<Volunteer> volunteerSpec = Specification.where(VolunteerSpecification.hasMinistry(ministry.getName()));
        List<Volunteer> volunteers = volunteerService.findAll(volunteerSpec);

        int remaining = maxVolunteers.intValue();
        List<Volunteer> selectedVolunteers = new ArrayList<>();
        Set<Long> processedGroupIds = new HashSet<>();

        while (remaining > 0 && !volunteers.isEmpty()) {
            Volunteer volunteer = selectRandomVolunteer(volunteers);

            if (volunteer.getGroup() != null && !processedGroupIds.contains(volunteer.getGroup().getId())) {
                List<Volunteer> volunteersGroup = validateGroup(volunteer, ministry, schedule, remaining);
                if (volunteersGroup != null) {
                    selectedVolunteers.addAll(volunteersGroup);
                    remaining -= volunteersGroup.size();
                    processedGroupIds.add(volunteer.getGroup().getId());
                }
            } else if (volunteer.getGroup() == null && !unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), volunteer.getId())) {
                selectedVolunteers.add(volunteer);
                remaining--;
            }

            volunteers.remove(volunteer);
        }

        return selectedVolunteers;
    }

    private Volunteer selectRandomVolunteer(List<Volunteer> volunteers) {
        Volunteer volunteer = volunteers.get((int) (Math.random() * volunteers.size()));
        volunteers.remove(volunteer);
        return volunteer;
    }

    private List<Volunteer> validateGroup(Volunteer volunteer, Ministry ministry, Schedule schedule, int remaining) {
        if (remaining == 0)
            return null;

        List<Volunteer> volunteersGroup = volunteer.getGroup().getVolunteers();
        int groupSize = volunteersGroup.size();

        if (groupSize > remaining)
            return null;

        for (Volunteer v : volunteersGroup) {
            if (!volunteerMinistryService.isVolunteerInMinistry(v.getId(), ministry.getId()))
                return null;

            if (unavailableDateService.isUnavailableDate(schedule.getStartDate(), schedule.getEndDate(), v.getId()))
                return null;
        }
        return volunteersGroup;
    }
}
