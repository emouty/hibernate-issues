package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static org.hibernate.annotations.OptimisticLockType.VERSION;
import static org.hibernate.annotations.SourceType.VM;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLocking;
import com.example.demo.local.Operator.OperatorPK;
import com.example.demo.local.Product.ProductPK;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
@OptimisticLocking(type = VERSION)
@DynamicUpdate
@Cacheable
@Cache(usage = READ_WRITE)
@Inheritance(strategy = JOINED)
@Table(name = "PRODUCTS")
public abstract class Product {
    protected Product(String productId, Operator operator) {
        this.productId = productId;
        this.operator = operator;
    }

    protected Product(String productId, Operator operator, Benefits benefits) {
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
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "OPERATOR_ID", referencedColumnName = "OPERATOR_ID", nullable = false)
    @JoinColumn(name = "COUNTRY", referencedColumnName = "COUNTRY", nullable = false)
    private Operator operator;

    @Column(name = "DESCRIPTION")
    @Setter
    private String description;

    @Embedded
    private Benefits benefits;

    @Version
    @CurrentTimestamp(source = VM)
    @Column(name = "MODIFICATION_DATE", nullable = false, insertable = true)
    private Instant modificationDate;

    @EqualsAndHashCode
    @ToString
    @Embeddable
    @NoArgsConstructor(access = PROTECTED)
    public static class ProductPK implements Serializable {

        private String productId;
        private OperatorPK operator;

        public ProductPK(String productId, OperatorPK operator) {
            this.productId = productId;
            this.operator = operator;
        }

        public ProductPK(String productId, String operatorID, Country country) {
            this.productId = productId;
            this.operator = new OperatorPK(operatorID, country);
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
