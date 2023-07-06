package com.example.demo.local.special;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static lombok.AccessLevel.PROTECTED;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(SpecialOperator.SpecialOperatorPK.class)
@Table(name = "SPECIAL_OPERATORS")
@Cacheable
@Cache(usage = READ_WRITE)
@NoArgsConstructor(access = PROTECTED)
public class SpecialOperator {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    private Provider provider;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    private String operatorId;

    @OneToMany(mappedBy = "operator", cascade = { PERSIST, MERGE, REMOVE }, orphanRemoval = true)
    private final Set<SpecialPricePoint> pricePoints = new HashSet<>();

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private Instant creationDate;

    @UpdateTimestamp
    @Column(name = "MODIFICATION_DATE", nullable = false)
    private Instant modificationDate;

    public void addPricePoint(SpecialPricePoint pricePoint) {
        pricePoint.setOperator(this);
        pricePoints.add(pricePoint);
    }

    public SpecialOperator(Provider provider, String operatorId) {
        this.provider = provider;
        this.operatorId = operatorId;
    }

    @EqualsAndHashCode
    @ToString
    @Getter
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor
    @Embeddable
    public static class SpecialOperatorPK implements Serializable {
        @Enumerated(EnumType.STRING)
        @Column(name = "PROVIDER_ID", nullable = false)
        Provider provider;

        @Column(name = "OPERATOR_ID", nullable = false)
        String operatorId;
    }
}
