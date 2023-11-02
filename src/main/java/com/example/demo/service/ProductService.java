package com.example.demo.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.FixedProduct;
import com.example.demo.local.FixedProductDao;
import com.example.demo.local.Product;
import com.example.demo.local.Product.ProductPK;
import com.example.demo.local.ProductDao;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    private final FixedProductDao fixedProductDao;

    @Transactional(propagation = REQUIRED)
    public void addProduct(Product product) {
        productDao.save(product);
    }

    @Transactional(propagation = REQUIRED)
    public Optional<Product> getProduct(ProductPK id) {
        return productDao.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Product> readProduct(ProductPK id) {
        return productDao.findById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteProduct(ProductPK id) {
        productDao.deleteById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteAllProducts() {
        productDao.deleteAll();
    }

    @Transactional(propagation = REQUIRED)
    public void addFixedProduct(FixedProduct product) {
        fixedProductDao.save(product);
    }

    @Transactional(propagation = REQUIRED)
    public Optional<FixedProduct> getFixedProduct(ProductPK id) {
        return fixedProductDao.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<FixedProduct> readFixedProduct(ProductPK id) {
        return fixedProductDao.findById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteFixedProduct(ProductPK id) {
        fixedProductDao.deleteById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteAllFixedProducts() {
        fixedProductDao.deleteAll();
    }
}
