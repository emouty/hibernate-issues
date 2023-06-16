package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import com.example.demo.local.Product.ProductPK;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
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
    @JoinColumn(nullable = false)
    private Operator operator;

    @Column(name = "DESCRIPTION")
    @Setter
    private String description;

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor(access = PUBLIC)
    public static class ProductPK implements Serializable {
        private String productId;
        private String operator;
    }
}
