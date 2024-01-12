package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
