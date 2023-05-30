package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static lombok.AccessLevel.PROTECTED;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "OPERATORS")
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@Cacheable
@Cache(usage = READ_WRITE)
public class Operator {

    public Operator(String operatorId) {
        this.operatorId = operatorId;
    }

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Column(name = "OPERATOR_ID", nullable = false)
    private String operatorId;

    @OneToMany(mappedBy = "operator", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
