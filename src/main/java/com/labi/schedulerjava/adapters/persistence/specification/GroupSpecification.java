package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Scanner;

public class GroupSpecification {

    public static Specification<Group> hasName(String groupName) {
        return (root, query, criteriaBuilder) -> {
            if (groupName == null || groupName.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + groupName.toLowerCase() + "%");
        };
    }

    public static Specification<Group> hasVolunteer(String volunteerName) {
        return (root, query, criteriaBuilder) -> {
            if (volunteerName == null || volunteerName.isEmpty()) return criteriaBuilder.conjunction();
            Join<Group, Volunteer> volunteerJoin = root.join("volunteers", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(volunteerJoin.get("name")), "%" + volunteerName.toLowerCase() + "%");
        };
    }

    public static Specification<Group> hasMinistryId(Long ministryId) {
        return (root, query, criteriaBuilder) -> {
            if (ministryId == null) return criteriaBuilder.conjunction();
            Join<Group, Volunteer> volunteerJoin = root.join("volunteers", JoinType.INNER);
            Join<Volunteer, VolunteerMinistry> volunteerMinistryJoin = volunteerJoin.join("volunteerMinistries", JoinType.INNER);
            return criteriaBuilder.and(criteriaBuilder.equal(volunteerMinistryJoin.get("ministry").get("id"), ministryId),
                    criteriaBuilder.isTrue(volunteerMinistryJoin.get("isActive")));
        };
    }
}
