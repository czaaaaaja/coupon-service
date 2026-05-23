package com.recruitment.empik.coupon_service.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class CouponEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private int version;

    @Column(unique = true, updatable = false)
    private String code;

    @Column(updatable = false)
    private LocalDate creationDate;

    private int maxUses;
    private int currentUses;
    private String country;
}
