package com.labi.schedulerjava.service;

import com.labi.schedulerjava.domain.User;
import com.labi.schedulerjava.domain.UserApprove;
import com.labi.schedulerjava.repository.UserApproveRepository;
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
