package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static com.example.demo.local.Country.USA;
import java.util.Optional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.local.FixedProduct;
import com.example.demo.local.Operator;
import com.example.demo.local.OperatorDao;
import com.example.demo.local.Product;
import com.example.demo.local.Product.ProductPK;

@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@SpringBootTest(properties = { "spring.jpa.properties.hibernate.cache.use_second_level_cache=true",
                               "spring.jpa.properties.hibernate.cache.use_query_cache=true" })
@AutoConfigureTestDatabase(replace = NONE)
@ExtendWith(SpringExtension.class)
class OperatorServiceWithCacheTest {

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
        ProductPK id = new ProductPK(string, operatorID, USA);
        String test = "test";
        Operator operator = new Operator(operatorID);
        //operatorDao.save(operator);
        Product product = new FixedProduct(string, operator);
        product.setDescription(test);
        productService.addProduct(product);
        assertThatNoException().isThrownBy(() -> operatorService.updateProductFromOperator(operatorID, string, "DescriptionTemp"));
        Product tempDescription = productService.readProduct(id).orElseThrow();
        assertThatNoException().isThrownBy(() -> operatorService.updateProductFromOperator(operatorID, string, "Description"));

        Product byId2 = productService.readProduct(id).orElseThrow();
        assertThat(byId2.getModificationDate()).isAfter(tempDescription.getModificationDate());
        assertThat(byId2.getDescription()).isEqualTo("Description");
    }

    @Test
    @Order(2)
    void shouldDeleteOperator() {
        // Given
        String operatorID = "operatorID2";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        // When
        operatorService.deleteOperator(new Operator.OperatorPK(operatorID, USA));

        //Then
        Optional<Operator> byId2 = operatorService.getOperator(operatorID);
        assertThat(byId2).isEmpty();
    }

    @Test
    @Order(3)
    void shouldDeleteAllOperators() {
        // Given
        String operatorID = "operatorID3";
        Operator operator = new Operator(operatorID);
        operatorDao.save(operator);
        // When
        operatorService.deleteAllOperators();
        // Then
        Optional<Operator> byId2 = operatorService.getOperator(operatorID);
        assertThat(byId2).isEmpty();
    }
}
