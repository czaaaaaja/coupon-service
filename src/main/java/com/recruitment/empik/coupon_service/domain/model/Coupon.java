package com.recruitment.empik.coupon_service.domain.model;

import com.recruitment.empik.coupon_service.exception.CouponException;
import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class Coupon {
    private String code;
    private LocalDate creationDate;
    private int maxUsages;
    private int usageCount;
    private String country;
}
