package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Group;
import org.springframework.data.jpa.domain.Specification;

public class GroupSpecification {

    public static Specification<Group> hasName(String groupName) {
        return (root, query, criteriaBuilder) -> {
            if (groupName == null || groupName.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + groupName.toLowerCase() + "%");
        };
    }
}
