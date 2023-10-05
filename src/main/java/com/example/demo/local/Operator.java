package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static com.example.demo.local.Country.USA;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static lombok.AccessLevel.PROTECTED;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

@Getter
@Entity
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "OPERATORS")
@IdClass(Operator.OperatorPK.class)
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@Cacheable
@Cache(usage = READ_WRITE)
public class Operator {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Convert(converter = CountryConverter.class)
    @Column(name = "COUNTRY", nullable = false)
    private Country country;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Column(name = "OPERATOR_ID", nullable = false)
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = "meta_operator_id", referencedColumnName = "ID")
    private MetaOperator metaOperator;

    @OneToMany(mappedBy = "operator",
               cascade = { PERSIST, MERGE, REMOVE },
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Operator(String operatorId) {
        this.operatorId = operatorId;
        // default country
        this.country = USA;
    }

    public void setMetaOperator(MetaOperator metaOperator) {
        this.metaOperator = metaOperator;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Embeddable
    @Value
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class OperatorPK implements Serializable {
        @NonFinal
        String operatorId;
        @NonFinal
        Country country;
    }

}
