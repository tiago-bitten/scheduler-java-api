package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ministry_activities")
public class MinistryActivities extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "total_volunteers")
    private Long totalVolunteers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id", nullable = false)
    private Ministry ministry;

    public MinistryActivities(String name, Long totalVolunteers, Ministry ministry) {
        this.name = name;
        this.totalVolunteers = totalVolunteers;
        this.ministry = ministry;
    }
}
