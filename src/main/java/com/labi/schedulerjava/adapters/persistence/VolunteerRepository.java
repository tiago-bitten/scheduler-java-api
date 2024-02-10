package com.labi.schedulerjava.adapters.persistence;

import com.labi.schedulerjava.core.domain.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    @Query("SELECT v FROM Volunteer v JOIN VolunteerMinistry vm ON v.id = vm.volunteer.id WHERE vm.ministry.id = ?1 AND vm.isActive = true")
    List<Volunteer> findAll(Long ministryId);

    List<Volunteer> findAll();

    @Query("SELECT v FROM Volunteer v WHERE v.cpf = ?1 AND v.birthDate = ?2")
    Optional<Volunteer> signInVolunteer(String cpf, LocalDate birthDate);

    Optional<Volunteer> findByCpf(String cpf);

    Optional<Volunteer> findByAccessKey(UUID accessKey);
}
