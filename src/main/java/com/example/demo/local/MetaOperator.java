package com.example.demo.local;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PROTECTED;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(access = PROTECTED)
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
@Cacheable
@Cache(usage = READ_WRITE)
@Entity
public class MetaOperator {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "metaOperator", fetch = EAGER, cascade = { PERSIST, MERGE })
    private final List<Operator> linkedOperators = new ArrayList<>();

    public MetaOperator(String id) {
        this.id = id;
    }

    public void addOperator(Operator operator) {
        operator.setMetaOperator(this);
        linkedOperators.add(operator);
    }
}
