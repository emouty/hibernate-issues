package com.example.demo.local.special;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.local.special.SpecialPricePoint.SpecialPricePointPK;

public interface SpecialPricePointDao
        extends CrudRepository<SpecialPricePoint, SpecialPricePointPK> {
}
