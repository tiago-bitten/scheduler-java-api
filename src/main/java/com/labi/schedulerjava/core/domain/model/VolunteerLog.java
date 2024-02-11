package com.labi.schedulerjava.core.domain.model;

import com.labi.schedulerjava.enums.Actions;
import com.labi.schedulerjava.enums.ActionsOrigin;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "volunteer_logs")
public class VolunteerLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User changedBy;

    @Column(name = "changed_at")
    private Instant changedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Actions action;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin")
    private ActionsOrigin origin;

    public VolunteerLog(Volunteer volunteer, User changedBy, Actions action, ActionsOrigin origin) {
        this.volunteer = volunteer;
        this.changedBy = changedBy;
        this.action = action;
        this.origin = origin;
        this.changedAt = Instant.now();
    }

    public VolunteerLog(Volunteer volunteer, Actions action, ActionsOrigin origin) {
        this.volunteer = volunteer;
        this.action = action;
        this.origin = origin;
        this.changedAt = Instant.now();
    }
}
