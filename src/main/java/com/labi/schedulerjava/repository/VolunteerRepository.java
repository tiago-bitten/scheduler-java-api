package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
