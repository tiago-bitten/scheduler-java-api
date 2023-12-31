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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_ministry_id")
    private VolunteerMinistry volunteerMinistry;

    public ScheduleVolunteersMinistries(Schedule schedule, VolunteerMinistry volunteerMinistry) {
        this.schedule = schedule;
        this.volunteerMinistry = volunteerMinistry;
    }
}
