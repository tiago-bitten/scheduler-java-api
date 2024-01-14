package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.User;
import com.labi.schedulerjava.dtos.CreateUserDto;
import com.labi.schedulerjava.dtos.ReadMinistryDto;
import com.labi.schedulerjava.dtos.ReadUserDto;
import com.labi.schedulerjava.enterprise.BusinessRuleException;
import com.labi.schedulerjava.enums.UserRole;
import com.labi.schedulerjava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMinistryService userMinistryService;

    @Autowired
    private UserApproveService userApproveService;

    public void create(CreateUserDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new BusinessRuleException("Este e-mail já está cadastrado");
        }

        userMinistryService.validateMinistries(dto.ministries());

        User user = new User(dto.name(), dto.email(), dto.password());
        userRepository.save(user);
        userMinistryService.associate(user, dto.ministries());
    }

    public List<ReadUserDto> findUsersToApprove() {
        return userRepository.findAll().stream()
                .filter(user -> user.getIsApproved().equals(false))
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

        userApproveService.register(userToApprove, userApprover);
    }
}
