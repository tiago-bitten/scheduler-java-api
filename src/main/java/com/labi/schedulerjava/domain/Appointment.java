package com.labi.schedulerjava.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_ministry_id")
    private VolunteerMinistry volunteerMinistry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointed_by_id")
    private User appointedBy;

    @Column(name = "appointed_at", nullable = false)
    private Instant appointedAt;

    public Appointment(Schedule schedule, VolunteerMinistry volunteerMinistry, User appointedBy) {
        this.schedule = schedule;
        this.volunteerMinistry = volunteerMinistry;
        this.appointedBy = appointedBy;
        this.appointedAt = Instant.now();
    }
}
