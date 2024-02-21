package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.MinistryActivities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinistryActivitiesRepository extends JpaRepository<MinistryActivities, Long> {
}
