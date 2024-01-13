package com.labi.schedulerjava.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "volunteerMinistry")
    private List<Assignment> assignments;

    public VolunteerMinistry(Volunteer volunteer, Ministry ministry) {
        this.volunteer = volunteer;
        this.ministry = ministry;
        this.isActive = true;
    }
}
