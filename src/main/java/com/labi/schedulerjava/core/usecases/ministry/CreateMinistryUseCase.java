package com.labi.schedulerjava.core.usecases.ministry;

import com.labi.schedulerjava.adapters.persistence.MinistryRepository;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.dtos.CreateMinistryDto;
import com.labi.schedulerjava.enterprise.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateMinistryUseCase {

    @Autowired
    private MinistryRepository ministryRepository;

    public void create(CreateMinistryDto dto) {
        if (ministryRepository.findByName(dto.name()).isPresent())
            throw new BusinessRuleException("Este ministério já está cadastrado");

        Ministry ministry = new Ministry(dto.name(), dto.description());
        ministryRepository.save(ministry);
    }
}
