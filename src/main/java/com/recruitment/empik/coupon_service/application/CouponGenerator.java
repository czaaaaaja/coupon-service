package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.api.request.CouponCreationRequest;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.infrastructure.persistence.SpringDataCouponRepository;
import com.recruitment.empik.coupon_service.infrastructure.persistence.mapping.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponGenerator {

    private final int DEFAULT_COUPON_LENGTH = 8;

    private final SpringDataCouponRepository repository;

    public Coupon createCoupon(CouponCreationRequest request) {
        String code = Optional.ofNullable(request.code())
                .orElse(RandomStringUtils.secure().next(DEFAULT_COUPON_LENGTH));
        Coupon coupon = new Coupon();
        coupon.setCode(code.toUpperCase());
        coupon.setCreationDate(LocalDate.now());
        coupon.setMaxUses(request.maxUses());
        coupon.setCountry("Poland");
        repository.save(CouponMapper.toEntity(coupon));
        return coupon;
    }
}
