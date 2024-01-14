package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.UserMinistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMinistryRepository extends JpaRepository<UserMinistry, Long> {
}
