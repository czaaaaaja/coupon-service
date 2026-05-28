package com.recruitment.empik.coupon_service.infrastructure.persistence.repository;

import com.recruitment.empik.coupon_service.infrastructure.persistence.model.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {

    public CouponEntity getCouponEntityByCode(String code);
}

