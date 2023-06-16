package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import java.util.Optional;
import org.junit.jupiter.api.Order;
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
class OperatorServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    OperatorDao operatorDao;
    @Autowired
    OperatorService operatorService;

    @Test
    @Order(1)
    void shouldEditProductTest() {
        String string = "ID";
        String operatorID = "operatorID";
        ProductPK id = new ProductPK(string, operatorID);
        String test = "test";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        Product product = new Product(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        assertThatNoException().isThrownBy(() -> operatorService.updateProductFromOperator(operatorID, string, "Description"));


        Optional<Product> byId2 = productService.readProduct(id);
        assertThat(byId2.orElseThrow().getDescription()).isEqualTo("Description");
    }

    @Test
    @Order(2)
    void shouldDeleteOperator() {
        String string = "ID2";
        String operatorID = "operatorID2";
        String test = "test";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        Product product = new Product(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        operatorService.deleteOperator(operatorID);

        Optional<Operator> byId2 = operatorService.getOperator(operatorID);
        assertThat(byId2).isEmpty();
    }

    @Test
    @Order(3)
    void shouldDeleteAllOperators() {
        String string = "ID3";
        String operatorID = "operatorID3";
        String test = "test";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        Product product = new Product(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        operatorService.deleteAllOperators();

        Optional<Operator> byId2 = operatorService.getOperator(operatorID);
        assertThat(byId2).isEmpty();
    }
}