package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.UserMinistryRepository;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.UserMinistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMinistryService {

    @Autowired
    private UserMinistryRepository userMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    public void associate(User user, List<Long> ministriesId) {
        ministriesId.forEach(id -> {
            Ministry ministry = ministryService.findById(id).get();

            UserMinistry userMinistry = new UserMinistry(user, ministry);
            userMinistryRepository.save(userMinistry);
        });
    }

    public boolean existsUserMinistryRelation(Long userId, Long ministryId) {
        return userMinistryRepository.existsByUserIdAndMinistryId(userId, ministryId);
    }
}
