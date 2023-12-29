package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
