package com.labi.schedulerjava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ministries")
public class Ministry extends BaseEntity {

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    public Ministry(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = Instant.now();
    }
}
