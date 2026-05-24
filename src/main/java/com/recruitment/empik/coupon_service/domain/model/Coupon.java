package com.recruitment.empik.coupon_service.domain.model;

import com.recruitment.empik.coupon_service.exception.CouponException;
import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
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

    public void use() {
        if (maxUsages > usageCount) {
            usageCount++;
        } else {
            throw new CouponWriteException(CouponWriteErrorCode.EXCEEDED_MAX_USAGES);
        }
    }
}
