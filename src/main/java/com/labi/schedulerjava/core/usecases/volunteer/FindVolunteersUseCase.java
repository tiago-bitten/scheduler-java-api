package com.labi.schedulerjava.core.usecases.volunteer;

import com.labi.schedulerjava.adapters.persistence.VolunteerRepository;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindVolunteersUseCase extends UseCase<FindVolunteersUseCase.InputValues, FindVolunteersUseCase.OutputValues> {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public OutputValues execute(InputValues input) {
        List<Volunteer> volunteers;

        if (input.getMinistryId() == null) {
            volunteers = volunteerRepository.findAll();
        } else {
            volunteers = volunteerRepository.findAll(input.getMinistryId());
        }

        List<ReadVolunteerDto> volunteerDtos = volunteers.stream()
                .map(this::toDtoWithActiveMinistries)
                .toList();

        return new OutputValues(volunteerDtos);
    }

    private ReadVolunteerDto toDtoWithActiveMinistries(Volunteer entity) {
        List<ReadMinistryDto> activeMinistries = entity.getVolunteerMinistries().stream()
                .filter(VolunteerMinistry::getIsActive)
                .map(volunteerMinistry -> new ReadMinistryDto(
                        volunteerMinistry.getMinistry().getId(),
                        volunteerMinistry.getMinistry().getName(),
                        volunteerMinistry.getMinistry().getDescription(),
                        volunteerMinistry.getMinistry().getColor(),
                        volunteerMinistry.getMinistry().getTotalVolunteers()
                )).toList();

        return new ReadVolunteerDto(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getBirthDate(),
                activeMinistries
        );
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        Long ministryId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        List<ReadVolunteerDto> volunteers;
    }
}

