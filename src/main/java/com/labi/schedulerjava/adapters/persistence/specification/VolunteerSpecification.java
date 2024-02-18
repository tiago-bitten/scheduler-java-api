package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
            return criteriaBuilder.and(
                    criteriaBuilder.like(criteriaBuilder.lower(ministryJoin.get("name")), "%" + ministry.toLowerCase() + "%"),
                    criteriaBuilder.isTrue(volunteerMinistryJoin.get("isActive"))
            );
        };
    }

    public static Specification<Volunteer> hasMinistryId(Long ministryId) {
        return (root, query, criteriaBuilder) -> {
            if (ministryId == null) return criteriaBuilder.conjunction();
            Join<Volunteer, VolunteerMinistry> volunteerMinistryJoin = root.join("volunteerMinistries", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(volunteerMinistryJoin.get("ministry").get("id"), ministryId),
                    criteriaBuilder.isTrue(volunteerMinistryJoin.get("isActive"))
            );
        };
    }

    public static Specification<Volunteer> isLinkedToAnyMinistry() {
        return (root, query, criteriaBuilder) -> {
            Join<Volunteer, VolunteerMinistry> volunteerMinistryJoin = root.join("volunteerMinistries", JoinType.INNER);
            return criteriaBuilder.isTrue(volunteerMinistryJoin.get("isActive"));
        };
    }
}
