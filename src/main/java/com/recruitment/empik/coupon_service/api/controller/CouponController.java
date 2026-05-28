package com.recruitment.empik.coupon_service.api.controller;

import com.recruitment.empik.coupon_service.api.request.CouponCreationRequest;
import com.recruitment.empik.coupon_service.api.request.CouponUseRequest;
import com.recruitment.empik.coupon_service.api.response.CouponErrorResponse;
import com.recruitment.empik.coupon_service.application.CouponCreator;
import com.recruitment.empik.coupon_service.application.CouponResolver;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.exception.CouponReadException;
import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponCreator couponCreator;
    private final CouponResolver couponResolver;

    @GetMapping("/{code}")
    public Coupon getCoupon(@PathVariable String code) {
        return couponResolver.getCoupon(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon generateCoupon(@RequestBody @Valid CouponCreationRequest request) {
        return couponCreator.createCoupon(request);
    }

    @PostMapping("/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon useCoupon(@PathVariable String code,
                            @RequestBody CouponUseRequest couponUseRequest) {
        return couponResolver.useCoupon(code, couponUseRequest);
    }

    //TODO move outside the controller
    @ExceptionHandler(CouponReadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CouponErrorResponse handleReadException(CouponReadException couponReadException) {
        return new CouponErrorResponse(couponReadException.getMessage(), Instant.now());
    }

    @ExceptionHandler(CouponWriteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CouponErrorResponse handleWriteException(CouponWriteException couponWriteException) {
        return new CouponErrorResponse(couponWriteException.getMessage(), Instant.now());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CouponErrorResponse handleOptimisticLockingException() {
        return new CouponErrorResponse(CouponWriteErrorCode.OPTIMISTIC_LOCK.getDefaultMessage(), Instant.now());
    }

}
