package com.labi.schedulerjava.core.usecases.user;

import com.labi.schedulerjava.adapters.persistence.UserRepository;
import com.labi.schedulerjava.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindUserByIdUseCaseUseCase {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
