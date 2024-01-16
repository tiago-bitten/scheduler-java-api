package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.UnavailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UnavailableDateRepository extends JpaRepository<UnavailableDate, Long> {
    Boolean findByDate(LocalDate date);
}
