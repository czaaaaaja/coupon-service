package com.recruitment.empik.coupon_service.infrastructure.persistence.model;

import com.recruitment.empik.coupon_service.exception.CouponWriteErrorCode;
import com.recruitment.empik.coupon_service.exception.CouponWriteException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "coupons",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"country", "code"})
        }
)
public class CouponEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Version
    private int version;

    @Column(updatable = false)
    private String code;

    //TODO
    @Column(updatable = false)
    private String country;

    @Column(updatable = false)
    private LocalDate creationDate;

    private int maxUsages;
    private int usageCount;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsageEntity> usages;

    public void addUsage(String userId) {
        if (maxUsages <= usageCount) {
            throw new CouponWriteException(CouponWriteErrorCode.EXCEEDED_MAX_USAGES);
        }
        if (usages.stream().anyMatch(usage -> userId.equals(usage.getUserId()))) {
            throw new CouponWriteException(CouponWriteErrorCode.CODE_USED_BY_THIS_USER);
        }
        UsageEntity usageEntity = new UsageEntity();
        usageEntity.setCoupon(this);
        usageEntity.setUserId(userId);
        usages.add(usageEntity);
        usageCount++;
    }
}
