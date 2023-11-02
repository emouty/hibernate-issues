package com.example.demo.local;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;
import static org.hibernate.annotations.OptimisticLockType.DIRTY;
import static lombok.AccessLevel.PROTECTED;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OptimisticLocking;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PROTECTED)
@Entity
@OptimisticLocking(type = DIRTY)
@DynamicUpdate
@Cacheable
@OnDelete(action = CASCADE)
@Table(name = "RANDOM_PRODUCT")
public class RandomProduct extends Product {

    @Setter
    @Column(name = "PRIZE_DESCRIPTION")
    private String PrizeDescription;

    public RandomProduct(String productId, Operator operator) {
        super(productId, operator);
    }

    public RandomProduct(String productId, Operator operator, Benefits benefits) {
        super(productId, operator, benefits);
    }
}
