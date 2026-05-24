package com.recruitment.empik.coupon_service.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"country", "code"})
        }
)
public class CouponEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private int version;

    @Column(updatable = false)
    private String code;

    //TODO
    @Column(updatable = false)
    private String country;

    @Column(updatable = false)
    private LocalDate creationDate;

    private int maxUsages;
    private int usageCount;
}
