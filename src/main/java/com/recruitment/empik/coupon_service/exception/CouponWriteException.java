package com.recruitment.empik.coupon_service.exception;

import lombok.Getter;

@Getter
public class CouponWriteException extends CouponException {
    private final CouponWriteErrorCode couponWriteErrorCode;

    public CouponWriteException(CouponWriteErrorCode couponWriteErrorCode) {
        this(couponWriteErrorCode.getDefaultMessage(), couponWriteErrorCode);
    }

    private CouponWriteException(String message, CouponWriteErrorCode couponWriteErrorCode) {
        super(message);
        this.couponWriteErrorCode = couponWriteErrorCode;
    }
}
