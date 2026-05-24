package com.recruitment.empik.coupon_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponWriteErrorCode {
    EXCEEDED_MAX_USAGES("The coupon usage limit has already been reached."),
    OPTIMISTIC_LOCK("The coupon has been modified by another transaction. Please try again.");
    private final String defaultMessage;
}
