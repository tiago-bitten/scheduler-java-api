package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class MinistrySpecification {

    public static Specification<Ministry> hasMinistry(String ministryName) {
        return (root, query, criteriaBuilder) -> {
            if (ministryName == null || ministryName.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + ministryName.toLowerCase() + "%");
        };
    }

    public static Specification<Ministry> hasVolunteer(String volunteerName) {
        return (root, query, criteriaBuilder) -> {
            if (volunteerName == null || volunteerName.isEmpty()) return criteriaBuilder.conjunction();
            Join<Ministry, VolunteerMinistry> volunteerMinistryJoin = root.join("volunteerMinistries", JoinType.INNER);
            Join<VolunteerMinistry, Volunteer> volunteerJoin = volunteerMinistryJoin.join("volunteer", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.like(criteriaBuilder.lower(volunteerJoin.get("name")), "%" + volunteerName.toLowerCase() + "%"),
                    criteriaBuilder.isTrue(volunteerMinistryJoin.get("isActive"))
            );
        };
    }
}
