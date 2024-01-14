package com.labi.schedulerjava.repository;

import com.labi.schedulerjava.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
