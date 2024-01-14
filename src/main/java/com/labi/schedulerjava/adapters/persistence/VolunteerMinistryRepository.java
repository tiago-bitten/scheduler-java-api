package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerMinistryRepository extends JpaRepository<VolunteerMinistry, Long> {
}
