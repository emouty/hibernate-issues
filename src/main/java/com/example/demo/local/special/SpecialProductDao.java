package com.example.demo.local.special;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.local.special.SpecialProduct.SpecialProductPK;

public interface SpecialProductDao extends CrudRepository<SpecialProduct, SpecialProductPK> {
}

