package com.recruitment.empik.coupon_service.exception;

import lombok.Getter;

@Getter
public class CouponReadException extends CouponException {
    private final CouponReadErrorCode couponReadErrorCode;

    public CouponReadException(CouponReadErrorCode couponReadErrorCode) {
        this(couponReadErrorCode.getDefaultMessage(), couponReadErrorCode);
    }
    private CouponReadException(String message, CouponReadErrorCode couponReadErrorCode) {
        super(message);
        this.couponReadErrorCode = couponReadErrorCode;
    }
}
