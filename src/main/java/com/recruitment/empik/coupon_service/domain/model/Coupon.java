package com.recruitment.empik.coupon_service.domain.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Coupon {
    private String code;
    private LocalDate creationDate;
    private int maxUses;
    private int currentUses;
    private String country;
}
