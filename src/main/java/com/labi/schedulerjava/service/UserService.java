package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.User;
import com.labi.schedulerjava.dtos.CreateUserDto;
import com.labi.schedulerjava.enums.UserRole;
import com.labi.schedulerjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(CreateUserDto dto) {
        userRepository.findByEmail(dto.email())
                .ifPresent(user -> {
                    throw new RuntimeException();
                });

        User user = new User(dto.name(), dto.email(), dto.password(), Instant.now(), UserRole.USER);
        userRepository.save(user);
    }
}
