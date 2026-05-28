package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.api.request.CouponCreationRequest;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import com.recruitment.empik.coupon_service.infrastructure.persistence.repository.CouponRepository;
import com.recruitment.empik.coupon_service.infrastructure.persistence.mapping.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponCreator {

    private final int DEFAULT_COUPON_LENGTH = 8;

    private final CouponRepository repository;

    public Coupon createCoupon(CouponCreationRequest request) {
        String code = Optional.ofNullable(request.code())
                .orElse(RandomStringUtils.secure().nextAlphanumeric(DEFAULT_COUPON_LENGTH));
        Coupon coupon = new Coupon();
        coupon.setCode(code.toUpperCase());
        coupon.setCreationDate(LocalDate.now());
        coupon.setMaxUsages(request.maxUsages());
        coupon.setCountry("Poland");
        try {
            repository.saveAndFlush(CouponMapper.toEntity(coupon));
        } catch (DataIntegrityViolationException exception) {
            throw new CouponWriteException(CouponWriteErrorCode.COUPON_ALREADY_EXISTS);
        }
        return coupon;
    }
}
