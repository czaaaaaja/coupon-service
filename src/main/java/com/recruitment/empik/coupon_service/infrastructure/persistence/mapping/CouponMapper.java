package com.recruitment.empik.coupon_service.infrastructure.persistence.mapping;

import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.infrastructure.persistence.model.CouponEntity;

public class CouponMapper {

    public static CouponEntity toEntity(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCode(coupon.getCode());
        couponEntity.setCreationDate(coupon.getCreationDate());
        couponEntity.setMaxUsages(coupon.getMaxUsages());
        couponEntity.setUsageCount(coupon.getUsageCount());
        couponEntity.setCountry(coupon.getCountry());
        return couponEntity;
    }

    public static Coupon toDomain(CouponEntity couponEntity) {
        Coupon coupon = new Coupon();
        coupon.setCode(couponEntity.getCode());
        coupon.setCreationDate(couponEntity.getCreationDate());
        coupon.setMaxUsages(couponEntity.getMaxUsages());
        coupon.setUsageCount(couponEntity.getUsageCount());
        coupon.setCountry(couponEntity.getCountry());
        return coupon;
    }

}
