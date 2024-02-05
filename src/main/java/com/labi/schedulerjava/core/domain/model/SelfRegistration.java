package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "self_registrations")
public class SelfRegistration extends BaseEntity {

    @Column(name = "link")
    private UUID link;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "used_at")
    private Instant usedAt;


    @OneToOne(mappedBy = "selfRegistration")
    @Column(name = "volunteer_id")
    private Volunteer volunteer;
}
