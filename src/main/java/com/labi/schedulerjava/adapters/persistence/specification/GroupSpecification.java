package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Group;
import com.labi.schedulerjava.core.domain.model.Volunteer;
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
}
