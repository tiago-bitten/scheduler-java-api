package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.SelfRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfRegistrationRepository extends JpaRepository<SelfRegistration, Long>{
}
