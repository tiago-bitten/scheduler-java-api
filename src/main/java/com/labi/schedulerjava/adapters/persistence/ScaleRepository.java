package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Scale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScaleRepository extends JpaRepository<Scale, Long> {
}
