package com.recruitment.empik.coupon_service.infrastructure.web;

import com.recruitment.empik.coupon_service.api.response.CouponErrorResponse;
import com.recruitment.empik.coupon_service.exception.CouponReadException;
import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CouponReadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CouponErrorResponse handleReadException(CouponReadException couponReadException) {
        return new CouponErrorResponse(couponReadException.getMessage(), Instant.now());
    }

    @ExceptionHandler(CouponWriteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponErrorResponse handleWriteException(CouponWriteException couponWriteException) {
        return new CouponErrorResponse(couponWriteException.getMessage(), Instant.now());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CouponErrorResponse handleOptimisticLockingException() {
        return new CouponErrorResponse(CouponWriteErrorCode.OPTIMISTIC_LOCK.getDefaultMessage(), Instant.now());
    }
}
