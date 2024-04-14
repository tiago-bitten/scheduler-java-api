package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByMinistryId(Long ministryId);
    List<Activity> findAllByMinistryIdAndIsActiveTrue(Long ministryId);
    boolean existsByIdAndMinistryId(Long activityId, Long ministryId);
}
