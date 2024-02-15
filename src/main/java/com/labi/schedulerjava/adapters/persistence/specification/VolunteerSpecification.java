package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class VolunteerSpecification {

    public static Specification<Volunteer> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Volunteer> hasMinistry(String ministry) {
        return (root, query, criteriaBuilder) -> {
            if (ministry == null || ministry.isEmpty()) return criteriaBuilder.conjunction();
            Join<Volunteer, VolunteerMinistry> volunteerMinistryJoin = root.join("volunteerMinistries", JoinType.INNER);
            Join<VolunteerMinistry, Ministry> ministryJoin = volunteerMinistryJoin.join("ministry", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(ministryJoin.get("name")), "%" + ministry.toLowerCase() + "%");
        };
    }
}
