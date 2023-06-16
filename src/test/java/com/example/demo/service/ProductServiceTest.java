package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.local.Operator;
import com.example.demo.local.OperatorDao;
import com.example.demo.local.Product;
import com.example.demo.local.Product.ProductPK;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @Autowired
    ProductService productService;
    @Autowired
    OperatorDao operatorDao;

    @Test
    void addProductTest() {
        String string = "ID";
        String operatorID = "operatorID";
        ProductPK id = new ProductPK(string, operatorID);
        String test = "test";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        Product product = new Product(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        Optional<Product> byId = productService.getProduct(id);
        assertThat(byId.orElseThrow().getDescription()).isEqualTo(test);
        Optional<Product> byId2 = productService.readProduct(id);
        assertThat(byId2.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
    }

    @Test
    void addProductAndReadFromCacheTest() {
        String string = "ID";
        String operatorID = "operatorID";
        ProductPK id = new ProductPK(string, operatorID);
        String test = "test";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        Product product = new Product(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        // getProduct has @Transactional(propagation = REQUIRES_NEW)
        // First read is made from DB
        Optional<Product> byId = productService.getProduct(id);
        assertThat(byId.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
        // Second read is from cache
        Optional<Product> byId2 = productService.getProduct(id);
        assertThat(byId2.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
    }


    @Test
    void addProductAndReadFromCacheReadOnlyTest() {
        String string = "ID";
        String operatorID = "operatorID";
        ProductPK id = new ProductPK(string, operatorID);
        String test = "test";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        Product product = new Product(string, operator);
        product.setDescription(test);
        productService.addProduct(product);
        // getProduct has @Transactional(propagation = REQUIRES_NEW) annotation
        // First read is made from DB
        Optional<Product> byId = productService.getProduct(id);
        assertThat(byId.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
        // readProduct has @Transactional(readOnly = true) annotation
        // Second read is from cache
        Optional<Product> byId2 = productService.readProduct(id);
        assertThat(byId2.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
    }

}