package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.api.request.CouponCreationRequest;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.infrastructure.persistence.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.StringUtil;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CouponCreatorTest {

    @Mock
    private CouponRepository repository;

    @InjectMocks
    private CouponCreator couponCreator;

    @Test
    public void shouldReturnAddedCoupon() {
        CouponCreationRequest request = new CouponCreationRequest(10, "CODE", "PL");
        Coupon coupon = couponCreator.createCoupon(request);

        assertThat(coupon.getCode()).isEqualTo("CODE");
    }

    @Test
    public void shouldGenerateRandomCode() {
        CouponCreationRequest request = new CouponCreationRequest(10, null, "PL");
        Coupon coupon = couponCreator.createCoupon(request);

        assertThat(coupon.getCode().length() == 8); //8 is the predefined length of automatically generated codes
    }
}
