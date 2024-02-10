package com.labi.schedulerjava.core.usecases.unavailabledate;

import com.labi.schedulerjava.adapters.persistence.UnavailableDateRepository;
import com.labi.schedulerjava.core.domain.model.UnavailableDate;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadUnavailableDateDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindUnavailableDatesByVolunteerUseCase extends UseCase<FindUnavailableDatesByVolunteerUseCase.InputValues, FindUnavailableDatesByVolunteerUseCase.OutputValues> {

    @Autowired
    private UnavailableDateRepository unavailableDateRepository;

    @Override
    public OutputValues execute(InputValues input) {
        List<UnavailableDate> unavailableDates = unavailableDateRepository.findByVolunteerId(input.getVolunteerId());
        return new OutputValues(
                unavailableDates.stream()
                        .map(u -> new ReadUnavailableDateDto(u.getId(), u.getVolunteer().getId(), u.getStartDate(), u.getEndDate(), u.getRrule()))
                        .toList()
        );
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long volunteerId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadUnavailableDateDto> unavailableDates;
    }
}
