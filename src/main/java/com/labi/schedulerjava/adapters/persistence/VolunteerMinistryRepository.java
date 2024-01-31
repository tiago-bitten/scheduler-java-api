package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Ministry;
import com.labi.schedulerjava.core.domain.model.Volunteer;
import com.labi.schedulerjava.core.domain.model.VolunteerMinistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolunteerMinistryRepository extends JpaRepository<VolunteerMinistry, Long> {
    Optional<VolunteerMinistry> findByVolunteerAndMinistry(Volunteer volunteer, Ministry ministry);
    List<VolunteerMinistry> findByMinistryId(Long ministryId);
}
