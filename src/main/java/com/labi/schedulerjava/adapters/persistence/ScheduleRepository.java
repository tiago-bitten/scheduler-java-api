package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
