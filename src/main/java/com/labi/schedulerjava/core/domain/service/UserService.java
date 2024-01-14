package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.core.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
