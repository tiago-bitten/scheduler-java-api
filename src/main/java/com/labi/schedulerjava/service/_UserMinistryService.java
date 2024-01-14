package com.labi.schedulerjava.service;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.UserMinistry;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.adapters.persistence.UserMinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class _UserMinistryService {

    @Autowired
    private UserMinistryRepository userMinistryRepository;

    @Autowired
    private _MinistryService ministryService;

    public void validateMinistries(List<Long> ministriesId) {
        if (ministriesId.isEmpty())
            throw new BusinessRuleException("É necessário informar ao menos um ministério");
        ministriesId.forEach(id -> {
            if (ministryService.findById(id).isEmpty())
                throw new BusinessRuleException("O ID informado " + id + " não corresponde a um ministério cadastrado");
        });
    }

    public void associate(User user, List<Long> ministriesId) {
        ministriesId.forEach(id -> {
            Ministry ministry = ministryService.findById(id).get();

            UserMinistry userMinistry = new UserMinistry(user, ministry);
            userMinistryRepository.save(userMinistry);
        });
    }
}
