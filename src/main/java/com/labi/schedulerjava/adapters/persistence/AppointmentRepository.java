package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
