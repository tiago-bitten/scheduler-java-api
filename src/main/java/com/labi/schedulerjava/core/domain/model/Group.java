package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "group")
    private List<Volunteer> volunteers;

    public Group(String name) {
        this.name = name;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addVolunteer(Volunteer volunteer) {
        this.volunteers.add(volunteer);
        volunteer.setGroup(this);
    }

    public void removeVolunteer(Volunteer volunteer) {
        this.volunteers.remove(volunteer);
        volunteer.setGroup(null);
    }
}
