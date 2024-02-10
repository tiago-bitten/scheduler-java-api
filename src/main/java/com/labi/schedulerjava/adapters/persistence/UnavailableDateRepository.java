package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.UnavailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnavailableDateRepository extends JpaRepository<UnavailableDate, Long> {

    List<UnavailableDate> findByVolunteerId(Long volunteerId);
}
