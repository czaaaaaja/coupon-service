package com.recruitment.empik.coupon_service.infrastructure.persistence;

import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import com.recruitment.empik.coupon_service.infrastructure.persistence.model.CouponEntity;
import com.recruitment.empik.coupon_service.infrastructure.persistence.model.UsageEntity;
import com.recruitment.empik.coupon_service.infrastructure.persistence.repository.CouponRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CouponRepositoryTest {

    @Autowired
    private CouponRepository repository;

    @Test
    public void shouldReturnExistingCoupon() {
        CouponEntity entity = repository.getCouponEntityByCode("CODE");
        assertThat(entity).isNotNull();
        assertThat(entity.getCode()).isEqualTo("CODE");
    }

    @Test
    public void shouldNotReturnNonExistingCoupon() {
        CouponEntity entity = repository.getCouponEntityByCode("NON VALID CODE");
        assertThat(entity).isNull();
    }

    @Test
    public void shouldAddNewCoupon() {
        CouponEntity entity = new CouponEntity();
        entity.setCode("CODE1");
        entity.setCountry("PL");
        entity.setMaxUsages(10);
        entity.setUsageCount(0);
        entity.setCreationDate(LocalDate.now());

        repository.saveAndFlush(entity);

        CouponEntity persistedEntity = repository.getCouponEntityByCode("CODE1");
        assertThat(persistedEntity).isNotNull();
        assertThat(persistedEntity.getCode()).isEqualTo("CODE1");
    }

    @Test
    public void shouldNotPersistSecondUsage() {

        CouponEntity persistedCoupon = repository.getCouponEntityByCode("CODE");
        persistedCoupon.addUsage("SOME_GUY", "PL");

        assertThatThrownBy(() -> repository.saveAndFlush(persistedCoupon))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void shouldNotPersistExceedingUsage() {
        CouponEntity persistedCoupon = repository.getCouponEntityByCode("USED_CODE");
        assertThatThrownBy(() -> persistedCoupon.addUsage("SOME_RANDOM_GUY", "PL"))
                .isInstanceOf(CouponWriteException.class);
    }

    @Test
    public void shouldThrowOptimisticLockException() {
        CouponEntity thread1Coupon = repository.getCouponEntityByCode("CODE");
        CouponEntity thread2Coupon = new CouponEntity();
        thread2Coupon.setUsageCount(thread1Coupon.getUsageCount());
        thread2Coupon.setId(thread1Coupon.getId());
        thread2Coupon.setUsages(thread1Coupon.getUsages());
        thread2Coupon.setCode(thread1Coupon.getCode());
        thread2Coupon.setCountry(thread1Coupon.getCountry());
        thread2Coupon.setMaxUsages(thread1Coupon.getMaxUsages());
        thread2Coupon.setVersion(thread1Coupon.getVersion());
        thread2Coupon.setCreationDate(thread1Coupon.getCreationDate());

        thread1Coupon.setMaxUsages(20);
        repository.saveAndFlush(thread1Coupon);

        thread2Coupon.setUsageCount(11);

        assertThatThrownBy(() -> repository.saveAndFlush(thread2Coupon))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }

}
