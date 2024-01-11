package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.ScheduleGrid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleGrid, Long> {
}
