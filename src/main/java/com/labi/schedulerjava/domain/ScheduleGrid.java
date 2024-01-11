package com.labi.schedulerjava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "schedules_grid")
public class ScheduleGrid extends BaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "current")
    private Boolean current;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "scheduleGrid")
    private List<ScheduleVolunteersMinistries> scheduleVolunteersMinistries;

    public ScheduleGrid(LocalDate date) {
        this.date = date;
        this.isActive = true;
        this.createdAt = Instant.now();

    }
}
