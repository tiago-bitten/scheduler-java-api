package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.Ministry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MinistryRepository extends JpaRepository<Ministry, Long> {
    Optional<Ministry> findByName(String name);
}
