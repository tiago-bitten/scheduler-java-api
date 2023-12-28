package com.labi.schedulerjava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public class BaseEntity {
    
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
}
