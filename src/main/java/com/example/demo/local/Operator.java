package com.example.demo.local;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

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


    @OneToMany(mappedBy = "operator", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
