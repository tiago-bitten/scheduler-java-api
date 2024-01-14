package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.UserApprove;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserApproveRepository extends JpaRepository<UserApprove, Long> {
}
