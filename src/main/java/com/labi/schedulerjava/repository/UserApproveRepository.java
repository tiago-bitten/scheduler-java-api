package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.UserApprove;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserApproveRepository extends JpaRepository<UserApprove, Long> {
}
