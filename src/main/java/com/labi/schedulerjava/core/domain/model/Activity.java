package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "activities")
public class Activity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "default_total_volunteers")
    private Long defaultTotalVolunteers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id", nullable = false)
    private Ministry ministry;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VolunteerActivity> volunteerActivities;

    public Activity(String name, Long defaultTotalVolunteers, Ministry ministry) {
        this.name = name;
        this.defaultTotalVolunteers = defaultTotalVolunteers;
        this.ministry = ministry;
    }
}
