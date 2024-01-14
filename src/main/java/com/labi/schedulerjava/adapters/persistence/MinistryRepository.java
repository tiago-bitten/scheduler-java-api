package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Ministry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MinistryRepository extends JpaRepository<Ministry, Long> {
    Optional<Ministry> findByName(String name);
}
