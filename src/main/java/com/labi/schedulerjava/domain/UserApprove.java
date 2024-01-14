package com.labi.schedulerjava.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_approves")
public class UserApprove extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "approved_id", nullable = false)
    private User approved;

    @ManyToOne
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Column(name = "approved_at", nullable = false)
    private Instant approvedAt;

    public UserApprove(User approved, User approver) {
        this.approved = approved;
        this.approver = approver;
        this.approvedAt = Instant.now();
    }
}
