package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import com.example.demo.local.Product.ProductPK;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

@Getter
@IdClass(ProductPK.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Entity
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@Cacheable
@Cache(usage = READ_WRITE)
@Table(name = "PRODUCTS")
public class Product {
    public Product(String productId, Operator operator) {
        this.productId = productId;
        this.operator = operator;
    }

    public Product(String productId, Operator operator, Benefits benefits) {
        this.productId = productId;
        this.operator = operator;
        this.benefits = benefits;
    }

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private String productId;

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @Getter
    @Setter
    @Cache(usage = READ_WRITE)
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "OPERATOR_ID", nullable = false)
    @JoinColumn(name = "COUNTRY", nullable = false)
    private Operator operator;

    @Column(name = "DESCRIPTION")
    @Setter
    private String description;

    @Embedded
    private Benefits benefits;

    @EqualsAndHashCode
    @ToString
    @Embeddable
    @NoArgsConstructor(access = PROTECTED)
    public static class ProductPK implements Serializable {
        private String productId;
        @Embedded
        @AttributeOverride(name = "operatorId", column = @Column(name = "OPERATOR_ID", nullable = false))
        @AttributeOverride(name = "country",
                           column = @Column(name = "COUNTRY", nullable = false))
        private Operator.OperatorPK operator;

        public ProductPK(String productId, Operator.OperatorPK operator) {
            this.productId = productId;
            this.operator = operator;
        }

        public ProductPK(String productId, String operatorID, Country country) {
            this.productId = productId;
            this.operator = new Operator.OperatorPK(operatorID, country);
        }
    }

    @Embeddable
    @Value
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class Benefits {

        @Embedded
        @NonFinal
        @Setter
        TypeOneBenefit credit;

        @Embedded
        @NonFinal
        @Setter
        TypeTwoBenefit data;
    }

    @Embeddable
    @Value
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class TypeOneBenefit {
        @NonFinal
        @Column(name = "BENEFIT_ONE_BASE_AMOUNT")
        BigDecimal baseAmount;

    }

    @Embeddable
    @Value
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class TypeTwoBenefit {

        @NonFinal
        @Column(name = "BENEFIT_TWO_BASE_AMOUNT")
        String baseAmount;
    }
}
