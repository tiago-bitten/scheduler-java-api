package com.labi.schedulerjava.domain;

import jakarta.persistence.*;
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
@Table(name = "volunteers")
public class Volunteer extends BaseEntity {

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "phone")
    public String phone;

    @Column(name = "birth_date")
    public LocalDate birthDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    @OneToMany(mappedBy = "volunteer")
    public List<VolunteerMinistry> volunteerMinistries;

    public Volunteer(String name, String lastName, String phone, LocalDate birthDate) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.createdAt = Instant.now();
    }
}