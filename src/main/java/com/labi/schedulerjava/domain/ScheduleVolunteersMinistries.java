package com.labi.schedulerjava.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "schedule_volunteers_ministries")
public class ScheduleVolunteersMinistries extends BaseEntity {

    @Column(name = "current")
    private Boolean current;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_grid_id")
    private ScheduleGrid scheduleGrid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_ministry_id")
    private VolunteerMinistry volunteerMinistry;

    public ScheduleVolunteersMinistries(Boolean current, ScheduleGrid scheduleGrid, VolunteerMinistry volunteerMinistry) {
        this.current = current;
        this.scheduleGrid = scheduleGrid;
        this.volunteerMinistry = volunteerMinistry;
    }
}
