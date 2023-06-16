package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static com.example.demo.local.PolicyType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@Cacheable
@Cache(usage = READ_WRITE)
public class Operator {

    public Operator(String operatorId) {
        this.operatorId = operatorId;
        this.tagPolicy = new OperatorTagPolicy(Set.of(), ALL);
    }

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Column(name = "OPERATOR_ID", nullable = false)
    private String operatorId;

    @Embedded
    private OperatorTagPolicy tagPolicy;

    @OneToMany(mappedBy = "operator", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Value
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    @Embeddable
    public static class OperatorTagPolicy {
        @NonFinal
        @ManyToMany(fetch = LAZY)
        @JoinTable(
                name = "OPERATOR_TO_TAG",
                joinColumns = {
                        @JoinColumn(name = "OPERATOR_ID", referencedColumnName = "OPERATOR_ID")
                },
                inverseJoinColumns = @JoinColumn(name = "TAG_NAME"))
        @Cache(usage = READ_WRITE)
        Set<Tag> exceptions;

        @NonFinal
        @Column(name = "TAG_POLICY_TYPE", nullable = false)
        @Enumerated(EnumType.STRING)
        PolicyType type;
    }
}
