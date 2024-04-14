package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByNameAndMinistryIdAndIsActiveTrue(String name, Long ministryId);
    List<Activity> findAllByMinistryId(Long ministryId);
    List<Activity> findAllByMinistryIdAndIsActiveTrue(Long ministryId);
    boolean existsByIdAndMinistryId(Long activityId, Long ministryId);
}
