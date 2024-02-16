package com.labi.schedulerjava.adapters.persistence.specification;

import com.labi.schedulerjava.core.domain.model.Ministry;
import org.springframework.data.jpa.domain.Specification;

public class MinistrySpecification {

    public static Specification<Ministry> hasMinistry(String ministryName) {
        return (root, query, criteriaBuilder) -> {
            if (ministryName == null || ministryName.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + ministryName.toLowerCase() + "%");
        };
    }
}
