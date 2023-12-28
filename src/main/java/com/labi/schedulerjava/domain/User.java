package com.labi.schedulerjava.domain;

import com.labi.schedulerjava.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "roles", nullable = false)
    private List<UserRole> roles;

    public User(String name, String email, String password, Instant createdAt, List<UserRole> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.roles = roles;
    }
}
