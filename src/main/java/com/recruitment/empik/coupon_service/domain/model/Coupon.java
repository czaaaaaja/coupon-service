package com.recruitment.empik.coupon_service.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Coupon {
    private String code;
    private LocalDate creationDate;
    private int maxUsages;
    private int usageCount;
    private String country;
}
