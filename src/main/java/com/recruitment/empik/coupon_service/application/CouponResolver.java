package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.infrastructure.persistence.SpringDataCouponRepository;
import com.recruitment.empik.coupon_service.infrastructure.persistence.mapping.CouponMapper;
import com.recruitment.empik.coupon_service.infrastructure.persistence.model.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponResolver {
    private final SpringDataCouponRepository repository;

    public Coupon getCoupon(String code) {
        return CouponMapper.toDomain(repository.getCouponEntityByCode(code));
    }

    public boolean useCoupon(String code) {
        return false;
    }

}
