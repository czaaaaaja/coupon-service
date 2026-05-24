package com.recruitment.empik.coupon_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponReadErrorCode {
    NON_EXISTING("Coupon does not exist.");
    private final String defaultMessage;

}
