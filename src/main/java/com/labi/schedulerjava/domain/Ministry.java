package com.labi.schedulerjava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ministries")
public class Ministry extends BaseEntity {

    @Column(name = "name", nullable = false)
    public String name;

    public Ministry(String name) {
        this.name = name;
    }
}
