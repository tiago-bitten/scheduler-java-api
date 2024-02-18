package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Appointment;
import org.springframework.data.jpa.domain.Specification;

public class AppointmentSpecification {

    public static Specification<Appointment> hasVolunteer(String volunteerName) {
        return (root, query, criteriaBuilder) -> {
            if (volunteerName == null || volunteerName.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(root.get("volunteerMinistry").get("volunteer").get("name"), "%" + volunteerName + "%");
        };
    }

    public static Specification<Appointment> hasSchedule(Long scheduleId) {
        return (root, query, criteriaBuilder) -> {
            if (scheduleId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("schedule").get("id"), scheduleId);
        };
    }
}
