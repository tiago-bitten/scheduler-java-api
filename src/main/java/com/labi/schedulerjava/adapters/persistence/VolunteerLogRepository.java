package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.VolunteerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerLogRepository extends JpaRepository<VolunteerLog, Long> {
}
