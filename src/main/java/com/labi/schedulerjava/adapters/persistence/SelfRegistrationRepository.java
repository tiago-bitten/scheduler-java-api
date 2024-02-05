package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.SelfRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SelfRegistrationRepository extends JpaRepository<SelfRegistration, Long>{


    Optional<SelfRegistration> findByLink(UUID link);
}
