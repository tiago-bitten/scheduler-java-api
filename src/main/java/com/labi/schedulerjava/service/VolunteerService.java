package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.Volunteer;
import com.labi.schedulerjava.dtos.CreateVolunteerDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadVolunteerDto;
import com.labi.schedulerjava.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private MinistryService ministryService;

    public void create(CreateVolunteerDto dto) {
        Volunteer volunteer = new Volunteer(dto.name(), dto.lastName(), dto.phone(), dto.birthDate());
        volunteerRepository.save(volunteer);
    }

    public void addMinistry(Long volunteerId, Long ministryId) {
        Ministry ministry = ministryService.findById(ministryId)
                .orElseThrow(() -> new RuntimeException("Ministry not found"));
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        volunteer.addMinistry(ministry);
        volunteerRepository.save(volunteer);
    }

    public List<ReadVolunteerDto> findAll(Long filter) {
        if (filter == null) {
            List<Volunteer> volunteers = volunteerRepository.findAll();
            return volunteers.stream().map(this::toDto).collect(Collectors.toList());
        }
        List<Volunteer> volunteers = volunteerRepository.findAllByMinistriesId(filter);
        return volunteers.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ReadVolunteerDto toDto(Volunteer entity) {
        return new ReadVolunteerDto(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getBirthDate().toString(),
                entity.getMinistries().stream().map(ministry -> new ReadMinistryDto(
                        ministry.getId(),
                        ministry.getName(),
                        ministry.getDescription()
                )).collect(Collectors.toList())
        );
    }
}
