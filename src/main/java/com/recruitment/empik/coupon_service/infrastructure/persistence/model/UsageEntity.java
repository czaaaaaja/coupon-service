package com.recruitment.empik.coupon_service.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "usages",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"coupon_id", "user_id"})
        }
)
public class UsageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponEntity coupon;

    private String userId;
}
