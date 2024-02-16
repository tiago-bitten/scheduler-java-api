package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Optional<Group> findByName(String name);
}
