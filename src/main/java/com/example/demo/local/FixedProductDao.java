package com.example.demo.local;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedProductDao extends CrudRepository<FixedProduct, Product.ProductPK> {

}
