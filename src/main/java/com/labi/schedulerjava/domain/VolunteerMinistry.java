package com.labi.schedulerjava.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "volunteer_ministries")
public class VolunteerMinistry extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id")
    private Ministry ministry;

    private Boolean isActive;

    public VolunteerMinistry(Volunteer volunteer, Ministry ministry, Boolean isActive) {
        this.volunteer = volunteer;
        this.ministry = ministry;
        this.isActive = isActive;
    }
}
