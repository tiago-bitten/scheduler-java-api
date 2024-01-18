package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Ministry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MinistryRepository extends JpaRepository<Ministry, Long> {
    Optional<Ministry> findByName(String name);

    @Query("SELECT m FROM Ministry m JOIN VolunteerMinistry vm ON m.id = vm.ministry.id WHERE vm.volunteer.id = ?1")
    List<Ministry> findAll(Long volunteerId);

    @Query("SELECT m FROM Ministry m JOIN UserMinistry um ON m.id = um.ministry.id WHERE um.user.id = ?1")
    List<Ministry> findAllByUserId(Long userId);
}
