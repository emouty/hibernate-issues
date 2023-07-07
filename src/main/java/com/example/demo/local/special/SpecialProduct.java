package com.example.demo.local.special;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static lombok.AccessLevel.PROTECTED;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.demo.local.special.SpecialPricePoint.SpecialPricePointPK;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "SPECIAL_PRODUCTS")
@Entity
@IdClass(SpecialProduct.SpecialProductPK.class)
@Cacheable
@Cache(usage = READ_WRITE)
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
public class SpecialProduct {
    public SpecialProduct(String productId,
                          SpecialPricePoint wholesalePrice) {
        this.productId = productId;
        this.wholesalePrice = wholesalePrice;
    }

    @Id
    @EqualsAndHashCode.Include

    @ToString.Include
    @Column(name = "PRODUCT_ID", nullable = false)
    private String productId;

    @Setter
    @ToString.Include
    @EqualsAndHashCode.Include
    @OneToOne(optional = false, cascade = { PERSIST, MERGE })
    @JoinColumn(name = "WHOLESALE_PRICE_AMOUNT",
                referencedColumnName = "PRICE_POINT",
                updatable = false,
                insertable = false)
    @JoinColumn(name = "OPERATOR_ID", referencedColumnName = "OPERATOR_ID", updatable = false, insertable = false)
    @JoinColumn(name = "PROVIDER_ID", referencedColumnName = "PROVIDER_ID", updatable = false, insertable = false)
    @MapsId
    private SpecialPricePoint wholesalePrice;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private Instant creationDate;

    @OptimisticLock(excluded = true)
    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE", nullable = false)
    private Instant modificationDate;

    @Value
    @Embeddable
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor
    public static class SpecialProductPK implements Serializable {
        @Embedded
        @NonFinal
        @AttributeOverride(name = "operator.provider", column = @Column(name = "PROVIDER_ID", nullable = false))
        @AttributeOverride(name = "operator.operatorId", column = @Column(name = "OPERATOR_ID", nullable = false))
        @AttributeOverride(name = "wholesalePrice", column = @Column(name = "WHOLESALE_PRICE_AMOUNT", nullable = false))
        SpecialPricePointPK wholesalePrice;

        @NonFinal
        String productId;
    }
}
