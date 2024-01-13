package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.Ministry;
import com.labi.schedulerjava.domain.User;
import com.labi.schedulerjava.domain.UserMinistry;
import com.labi.schedulerjava.repository.UserMinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMinistryService {

    @Autowired
    private UserMinistryRepository userMinistryRepository;

    @Autowired
    private MinistryService ministryService;

    public void validateMinistries(List<Long> ministriesId) {
        ministriesId.forEach(ministry -> {
            if (ministryService.findById(ministry).isEmpty())
                throw new RuntimeException("Ministry not found");
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
