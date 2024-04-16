package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import org.springframework.data.jpa.domain.Specification;

public class VolunteerMinistrySpecification {

    public static Specification<VolunteerMinistry> hasMinistryId(Long ministryId) {
        return (root, query, criteriaBuilder) -> {
            if (ministryId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("ministry").get("id"), ministryId),
                    criteriaBuilder.isTrue(root.get("isActive"))
            );
        };
    }

    public static Specification<VolunteerMinistry> hasVolunteerName(String volunteerName) {
        return (root, query, criteriaBuilder) -> {
            if (volunteerName == null || volunteerName.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.and(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("volunteer").get("name")), "%" + volunteerName.toLowerCase() + "%"),
                    criteriaBuilder.isTrue(root.get("isActive"))
            );
        };
    }
}
