package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scales")
public class Scale extends BaseEntity {

    @Column(name = "name", nullable = false)
    private Long maxVolunteers;

    @Column(name = "generated_at", nullable = false, updatable = false)
    private Instant generatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id", nullable = false)
    private Ministry ministry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by_id", nullable = false)
    private User generatedBy;

    public Scale(Long maxVolunteers, Schedule schedule, Ministry ministry, User generatedBy) {
        this.maxVolunteers = maxVolunteers;
        this.generatedAt = Instant.now();
        this.schedule = schedule;
        this.ministry = ministry;
        this.generatedBy = generatedBy;
    }
}
