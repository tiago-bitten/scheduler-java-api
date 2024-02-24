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

    @Autowired
    private AppointmentService appointmentService;

    public boolean checkVolunteersSize(Map<Long, Long> activityIdVolunteers) {
        return activityIdVolunteers.values().stream().allMatch(max -> max > 0);
    }

    public List<Volunteer> createIndividualScale(Ministry ministry, Schedule schedule, Long numberOfVolunteers) {
        Specification<Volunteer> volunteerSpec = Specification.where(VolunteerSpecification.hasMinistry(ministry.getName()));
        List<Volunteer> volunteers = volunteerService.findAll(volunteerSpec);

        int remaining = numberOfVolunteers.intValue();
        List<Volunteer> selectedVolunteers = new ArrayList<>();
        Set<Long> processedGroupIds = new HashSet<>();

        while (remaining > 0 && !volunteers.isEmpty()) {
            Volunteer volunteer = selectRandomVolunteer(volunteers);

            if (appointmentService.validateAppointment(schedule, volunteer.getId())) {
                continue;
            }

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
