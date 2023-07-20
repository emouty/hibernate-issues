package com.example.demo.local.special;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.CascadeType.REMOVE;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import com.example.demo.local.special.SpecialOperator.SpecialOperatorPK;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "SPECIAL_OPERATOR_PRICES_POINTS")
@IdClass(SpecialPricePoint.SpecialPricePointPK.class)
@NoArgsConstructor(access = PROTECTED)
public class SpecialPricePoint {

    public SpecialPricePoint(SpecialOperator operator,
                             String wholesalePrice) {
        this.operator = operator;
        this.wholesalePrice = wholesalePrice;
    }

    @ManyToOne
    @ToString.Include
    @Setter(PACKAGE)
    @EqualsAndHashCode.Include
    @JoinColumn(name = "PROVIDER_ID", referencedColumnName = "PROVIDER_ID")
    @JoinColumn(name = "OPERATOR_ID", referencedColumnName = "OPERATOR_ID")
    @MapsId
    private SpecialOperator operator;

    @Id
    @Column(name = "PRICE_POINT", nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    String wholesalePrice;

    @OneToOne(mappedBy = "wholesalePrice", cascade = { REFRESH, MERGE, REMOVE }, orphanRemoval = true)
    @Cache(usage = READ_WRITE)
    private SpecialProduct product;

    public void setProduct(SpecialProduct product) {
        product.setWholesalePrice(this);
        this.product = product;

    }

    @Value
    @Embeddable
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor
    public static class SpecialPricePointPK implements Serializable {
        @Embedded
        @NonFinal
        @AttributeOverride(name = "provider", column = @Column(name = "PROVIDER_ID", nullable = false))
        @AttributeOverride(name = "operatorId", column = @Column(name = "OPERATOR_ID", nullable = false))
        SpecialOperatorPK operator;

        @NonFinal
        String wholesalePrice;
    }
}
