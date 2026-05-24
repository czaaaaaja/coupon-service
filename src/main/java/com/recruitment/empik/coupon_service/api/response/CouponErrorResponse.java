package com.recruitment.empik.coupon_service.api.response;

import java.time.Instant;

public record CouponErrorResponse(String message, Instant timestamp) {
}
