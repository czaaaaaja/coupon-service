package com.recruitment.empik.coupon_service.api.request;

import jakarta.validation.constraints.Positive;

public record CouponCreationRequest(@Positive int maxUses, String code) {}
