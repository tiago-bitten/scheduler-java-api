package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "total_volunteers")
    private Long totalVolunteers;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "ministry")
    private List<VolunteerMinistry> volunteerMinistries;

    @OneToMany(mappedBy = "ministry")
    private List<UserMinistry> userMinistries;

    public Ministry(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.totalVolunteers = 0L;
        this.createdAt = Instant.now();
    }
}
