package com.labi.schedulerjava.core.domain.model;

import com.labi.schedulerjava.enums.VolunteerOrigin;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "volunteers")
public class Volunteer extends BaseEntity {

    @Column(name = "access_key", nullable = false, updatable = false)
    private UUID accessKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String cpf;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_image_url")
    public String profileImageURL;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "origin", nullable = false)
    @Enumerated(EnumType.STRING)
    private VolunteerOrigin origin;

    @OneToMany(mappedBy = "volunteer")
    private List<VolunteerMinistry> volunteerMinistries;

    @OneToMany(mappedBy = "volunteer")
    private List<UnavailableDate> unavailableDates;

    @ManyToOne(fetch = FetchType.LAZY)
    public Group group;

    @OneToMany(mappedBy = "volunteer")
    private List<VolunteerLog> volunteerLogs;

    public Volunteer(String name, String lastName, String cpf, String phone, LocalDate birthDate, VolunteerOrigin origin) {
        this.accessKey = UUID.randomUUID();
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.phone = phone;
        this.birthDate = birthDate;
        this.createdAt = Instant.now();
        this.origin = origin;
    }
}
