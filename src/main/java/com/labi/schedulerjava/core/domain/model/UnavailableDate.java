package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "unavailable_dates")
public class UnavailableDate extends BaseEntity {

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "rrule")
    private String rrule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    public UnavailableDate(LocalDateTime startDate, LocalDateTime endDate, String rrule, Volunteer volunteer) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.rrule = rrule;
        this.volunteer = volunteer;
    }
}
