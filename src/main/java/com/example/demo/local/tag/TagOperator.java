package com.example.demo.local.tag;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.NonFinal;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.util.Set;

import static com.example.demo.local.tag.PolicyType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Getter
@Entity
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "TAG_OPERATORS")
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@Cacheable
@org.hibernate.annotations.Cache(usage = READ_WRITE)
public class TagOperator {

    public TagOperator(String operatorId) {
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
        @org.hibernate.annotations.Cache(usage = READ_WRITE)
        Set<Tag> exceptions;

        @NonFinal
        @Column(name = "TAG_POLICY_TYPE", nullable = false)
        @Enumerated(EnumType.STRING)
        PolicyType type;
    }
}
