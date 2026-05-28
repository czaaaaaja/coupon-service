package com.recruitment.empik.coupon_service.application;

import com.recruitment.empik.coupon_service.api.request.CouponUseRequest;
import com.recruitment.empik.coupon_service.domain.model.Coupon;
import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import com.recruitment.empik.coupon_service.infrastructure.persistence.mapping.CouponMapper;
import com.recruitment.empik.coupon_service.infrastructure.persistence.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CouponResolverTest {

    @Mock
    private CouponRepository repository;

    @InjectMocks
    private CouponResolver couponResolver;

    @Test
    public void get_shouldReturnCouponWhenExistsAndIsCaseInsensitive() {
        Coupon expectedCoupon = createCoupon();
        when(repository.getCouponEntityByCode("CODE")).thenReturn(CouponMapper.toEntity(expectedCoupon));

        Coupon actualCoupon = couponResolver.getCoupon("code");

        assertThat(actualCoupon).isNotNull();
        assertThat(actualCoupon.getCode()).isEqualTo("CODE");

    }

    @Test
    public void get_shouldThrowExceptionWhenIpNotCorrect() {

        CouponUseRequest request = new CouponUseRequest("SOME_USER", "NOT AN IP");
        assertThatThrownBy(() -> couponResolver.useCoupon("CODE", request))
                .isInstanceOf(CouponWriteException.class)
                .hasMessageContaining(CouponWriteErrorCode.COUNTRY_INVALID.getDefaultMessage());
    }

    private Coupon createCoupon() {
        LocalDate creationDate = LocalDate.now();
        Coupon coupon = new Coupon();
        coupon.setCode("CODE");
        coupon.setCreationDate(creationDate);
        coupon.setCountry("PL");
        coupon.setMaxUsages(10);
        return coupon;
    }
}
