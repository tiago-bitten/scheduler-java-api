package com.labi.schedulerjava.core.domain.service;

import com.labi.schedulerjava.adapters.persistence.UserApproveRepository;
import com.labi.schedulerjava.core.domain.model.User;
import com.labi.schedulerjava.core.domain.model.UserApprove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApproveService {

    @Autowired
    private UserApproveRepository userApproveRepository;

    public void register(User approved, User approver) {
        UserApprove userApprove = new UserApprove(approved, approver);
        userApproveRepository.save(userApprove);
    }
}
