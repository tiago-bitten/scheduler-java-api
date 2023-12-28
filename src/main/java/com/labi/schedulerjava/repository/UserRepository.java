package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
