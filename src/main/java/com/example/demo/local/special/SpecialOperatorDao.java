package com.example.demo.local.special;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.local.special.SpecialOperator.SpecialOperatorPK;

public interface SpecialOperatorDao
        extends CrudRepository<SpecialOperator, SpecialOperatorPK> {
}
