package com.recruitment.empik.coupon_service.api.controller;

import com.recruitment.empik.coupon_service.api.request.CouponCreationRequest;
import com.recruitment.empik.coupon_service.application.CouponGenerator;
import com.recruitment.empik.coupon_service.application.CouponResolver;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponGenerator couponGenerator;
    private final CouponResolver couponResolver;

    @GetMapping("/{code}")
    public Coupon getCoupon(@PathVariable String code) {
        return couponResolver.getCoupon(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon generateCoupon(@RequestBody @Valid CouponCreationRequest request) {
        return couponGenerator.createCoupon(request);
    }



}
