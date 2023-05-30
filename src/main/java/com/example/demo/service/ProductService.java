package com.example.demo.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.Product;
import com.example.demo.local.Product.ProductPK;
import com.example.demo.local.ProductDao;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    @Transactional(propagation = REQUIRES_NEW)
    public Product addProduct(Product product) {
        return productDao.save(product);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Optional<Product> getProduct(ProductPK id) {
        return productDao.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Product> readProduct(ProductPK id) {
        return productDao.findById(id);
    }


}
