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
@Table(name = "volunteer_activities")
public class VolunteerActivity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_activities_id", nullable = false)
    private MinistryActivities ministryActivities;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    public VolunteerActivity(Volunteer volunteer, MinistryActivities ministryActivities) {
        this.volunteer = volunteer;
        this.ministryActivities = ministryActivities;
        this.assignedAt = Instant.now();
    }
}

