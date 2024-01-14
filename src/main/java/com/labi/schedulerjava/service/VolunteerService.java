package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.domain.VolunteerMinistry;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import com.labi.schedulerjava.enterprise.BusinessRuleException;
import com.labi.schedulerjava.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerMinistryService volunteerMinistryService;

    @Autowired
    private MinistryService ministryService;

    public void create(CreateVolunteerDto dto) {
        Volunteer volunteer = new Volunteer(dto.name(), dto.lastName(), dto.phone(), dto.birthDate());
        volunteerRepository.save(volunteer);
    }

    public void addMinistry(Long volunteerId, Long ministryId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + volunteerId + " não corresponde a um voluntário cadastrado"));
        Ministry ministry = ministryService.findById(ministryId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + ministryId + " não corresponde a um ministério cadastrado"));

        volunteerMinistryService.associate(volunteer, ministry);
    }

    public List<ReadVolunteerDto> findAll(Long ministryId) {
        if (ministryId == null)
            return volunteerRepository.findAll().stream()
                    .map(this::toDto)
                    .toList();

        List<Volunteer> volunteers = volunteerRepository.findAll().stream()
                .filter(volunteer -> volunteer.getVolunteerMinistries().stream()
                        .anyMatch(volunteerMinistry -> volunteerMinistry.getMinistry().getId().equals(ministryId) && volunteerMinistry.getIsActive()))
                .toList();

        return volunteers.stream()
                .map(this::toDto)
                .toList();
    }

    public ReadVolunteerDto toDto(Volunteer entity) {
        return new ReadVolunteerDto(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getBirthDate().toString(),
                entity.getVolunteerMinistries().stream()
                        .map(volunteerMinistry -> new ReadMinistryDto(
                                volunteerMinistry.getMinistry().getId(),
                                volunteerMinistry.getMinistry().getName(),
                                volunteerMinistry.getMinistry().getDescription()))
                        .toList());
    }
}
