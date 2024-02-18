package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Appointment;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class AppointmentSpecification {

    public static Specification<Appointment> hasVolunteer(String volunteerName) {
        return (root, query, criteriaBuilder) -> {
            if (volunteerName == null || volunteerName.isEmpty()) return criteriaBuilder.conjunction();
            Join<Appointment, VolunteerMinistry> volunteerMinistryJoin = root.join("volunteerMinistry", JoinType.INNER);
            Join<VolunteerMinistry, Volunteer> volunteerJoin = volunteerMinistryJoin.join("volunteer", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(volunteerJoin.get("name")), "%" + volunteerName.toLowerCase() + "%");
        };
    }

    public static Specification<Appointment> hasSchedule(Long scheduleId) {
        return (root, query, criteriaBuilder) -> {
            if (scheduleId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("schedule").get("id"), scheduleId);
        };
    }
}
