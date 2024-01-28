package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    @Query("SELECT v FROM Volunteer v JOIN VolunteerMinistry vm ON v.id = vm.volunteer.id WHERE vm.ministry.id = ?1 AND vm.isActive = true")
    List<Volunteer> findAll(Long ministryId);

    List<Volunteer> findAll();
}
