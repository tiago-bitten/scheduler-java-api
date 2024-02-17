package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.UnavailableDateRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.UnavailableDate;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnavailableDateService {

    @Autowired
    private UnavailableDateRepository unavailableDateRepository;

    @Autowired
    private VolunteerService volunteerService;

    public boolean isUnavailableDate(LocalDateTime startDate, LocalDateTime endDate, Long volunteerId) {
        Volunteer volunteer = volunteerService.findById(volunteerId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + volunteerId + " não corresponde a um voluntário cadastrado"));

        List<UnavailableDate> unavailableDates = volunteer.getUnavailableDates();

        for (UnavailableDate unavailableDate : unavailableDates) {
            if (unavailableDate.getStartDate().isBefore(endDate) && unavailableDate.getEndDate().isAfter(startDate)) {
                return true;
            }
        }
        return false;
    }
}
