package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
