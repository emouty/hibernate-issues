package com.example.demo.local;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.local.Product.ProductPK;

@Repository
public interface ProductDao extends CrudRepository<Product, ProductPK> {


}
