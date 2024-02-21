package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.VolunteerActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerActivityRepository extends JpaRepository<VolunteerActivity, Long>{
}
