package com.labi.schedulerjava.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ministries")
public class Ministry extends BaseEntity {

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    @OneToMany(mappedBy = "ministry")
    public List<VolunteerMinistry> volunteerMinistries;

    public Ministry(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = Instant.now();
    }
}
