package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.GroupRepository;
import com.labi.schedulerjava.core.domain.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }
}
