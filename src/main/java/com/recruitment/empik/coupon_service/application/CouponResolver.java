package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.exception.CouponReadErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponReadException;
import com.recruitment.empik.coupon_service.infrastructure.persistence.SpringDataCouponRepository;
import com.recruitment.empik.coupon_service.infrastructure.persistence.mapping.CouponMapper;
import com.recruitment.empik.coupon_service.infrastructure.persistence.model.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponResolver {
    private final SpringDataCouponRepository repository;

    public Coupon getCoupon(String code) {
        return CouponMapper.toDomain(getCouponEntityByCode(code));
    }

    @Transactional
    public void useCoupon(String code) {
        CouponEntity couponEntity = getCouponEntityByCode(code);
        Coupon coupon = CouponMapper.toDomain(couponEntity);
        try{
            Thread.sleep(5000);
        } catch (Exception e) {}
        coupon.use();
        CouponMapper.updateUsageCount(coupon, couponEntity);
        repository.save(couponEntity);
    }

    private CouponEntity getCouponEntityByCode(String code) {
        CouponEntity couponEntity = repository.getCouponEntityByCode(code.toUpperCase());
        if (couponEntity == null) {
            throw new CouponReadException(CouponReadErrorCode.NON_EXISTING);
        }
        return couponEntity;
    }

}
