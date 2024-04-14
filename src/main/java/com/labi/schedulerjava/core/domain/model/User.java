package com.labi.schedulerjava.core.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @Column(name = "is_super_user", nullable = false)
    private Boolean isSuperUser;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "user")
    private List<UserMinistry> userMinistries;

    @OneToMany(mappedBy = "approved")
    private List<UserApprove> approveds;

    @OneToMany(mappedBy = "approver")
    private List<UserApprove> approvers;

    @OneToMany(mappedBy = "changedBy")
    private List<VolunteerLog> volunteerLogs;

    @OneToMany(mappedBy = "appointedBy")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "generatedBy")
    private List<Scale> scales;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        if (this.name.equals("Admin")) {
            this.isSuperUser = true;
            this.isApproved = true;
        } else {
            this.isSuperUser = false;
            this.isApproved = false;
        }
    }
}
