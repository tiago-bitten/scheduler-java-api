package com.labi.schedulerjava.service;

import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.dtos.CreateUserDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadUserDto;
import com.labi.schedulerjava.core.domain.exception.BusinessRuleException;
import com.labi.schedulerjava.adapters.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class _UserService {

    @Autowired
    private UserRepository userRepository;

    public List<ReadUserDto> findUsersToApprove() {
        return userRepository.usersToApprove().stream()
                .map(user -> new ReadUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getIsApproved(),
                        user.getCreatedAt(),
                        user.getUserMinistries().stream()
                                .map(userMinistry -> new ReadMinistryDto(
                                        userMinistry.getMinistry().getId(),
                                        userMinistry.getMinistry().getName(),
                                        userMinistry.getMinistry().getDescription()
                                )).toList()
                )).toList();
    }

    public void approve(Long userToApproveId, Long userApproverId, Boolean isSuperUser) {
        User userToApprove = userRepository.findById(userToApproveId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + userToApproveId + " não corresponde a um usuário cadastrado"));

        User userApprover = userRepository.findById(userApproverId)
                .orElseThrow(() -> new BusinessRuleException("O ID informado " + userApproverId + " não corresponde a um usuário cadastrado"));

        if (!userApprover.getIsApproved() && !userApprover.getIsSuperUser())
            throw new BusinessRuleException("O usuário aprovador não está aprovado ou não possui permissão para aprovar usuários");

        if (userToApprove.getIsApproved())
            throw new BusinessRuleException("O usuário já está aprovado");

        userToApprove.setIsApproved(true);
        userToApprove.setIsSuperUser(isSuperUser);
        userRepository.save(userToApprove);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
