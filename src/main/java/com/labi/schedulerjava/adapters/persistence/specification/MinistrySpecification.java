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
        return (root, query, CriteriaBuilder) -> {
            if (volunteerName == null || volunteerName.isEmpty()) return CriteriaBuilder.conjunction();
            Join<Ministry, VolunteerMinistry> volunteerMinistryJoin = root.join("volunteerMinistries", JoinType.INNER);
            Join<VolunteerMinistry, Volunteer> volunteerJoin = volunteerMinistryJoin.join("volunteer", JoinType.INNER);
            return CriteriaBuilder.like(CriteriaBuilder.lower(volunteerJoin.get("name")), "%" + volunteerName.toLowerCase() + "%");
        };
    }
}
