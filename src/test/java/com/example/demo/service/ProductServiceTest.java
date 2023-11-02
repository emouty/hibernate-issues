package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.demo.local.Country.USA;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.local.FixedProduct;
import com.example.demo.local.Operator;
import com.example.demo.local.Product;
import com.example.demo.local.Product.Benefits;
import com.example.demo.local.Product.ProductPK;
import com.example.demo.local.Product.TypeOneBenefit;
import com.example.demo.local.Product.TypeTwoBenefit;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(properties = { "spring.jpa.properties.hibernate.cache.use_second_level_cache=false",
                               "spring.jpa.properties.hibernate.cache.use_query_cache=false" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @Autowired
    ProductService productService;
    @Autowired
    OperatorService operatorService;

    @Test
    void addProductTest() {
        String string = "ID";
        String operatorID = "operatorID";
        ProductPK id = new ProductPK(string, operatorID, USA);
        String test = "test";
        Operator operator = new Operator(operatorID);
        Product product = new FixedProduct(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        Optional<Product> byId = productService.getProduct(id);
        assertThat(byId.orElseThrow().getDescription()).isEqualTo(test);
        Optional<Product> byId2 = productService.readProduct(id);
        assertThat(byId2.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
    }

    @Test
    void addFixedProductTest() {
        String string = "ID";
        String operatorID = "operatorID";
        ProductPK id = new ProductPK(string, operatorID, USA);
        String test = "test";
        Operator operator = new Operator(operatorID);
        FixedProduct product = new FixedProduct(string, operator);
        product.setDescription(test);
        productService.addFixedProduct(product);

        Optional<FixedProduct> byId = productService.getFixedProduct(id);
        assertThat(byId.orElseThrow().getDescription()).isEqualTo(test);
        Optional<FixedProduct> byId2 = productService.readFixedProduct(id);
        assertThat(byId2.orElseThrow().getOperator().getOperatorId()).isEqualTo(operatorID);
    }

    @Test
    void shouldDeleteProduct() {
        // Given
        String string = "ID2";
        String operatorID = "operatorID2";
        String test = "test";
        Operator operator = new Operator(operatorID);
        //operatorService.addOperator(operator);
        Product product = new FixedProduct(string, operator);
        product.setDescription(test);
        productService.addProduct(product);

        // When
        ProductPK productPK = new ProductPK(string, operatorID, USA);
        productService.deleteProduct(productPK);

        // Then
        Optional<Product> byId2 = productService.getProduct(productPK);
        assertThat(byId2).isEmpty();
    }

    @Test
    void shouldDeleteProductsWithBenefitsFromOperator() {
        // Given
        String productId1 = "ID1";
        String productId2 = "ID2";
        String operatorID = "operatorID2";
        String test = "test";
        Operator operator = new Operator(operatorID);
        //operatorService.addOperator(operator);

        TypeOneBenefit typeOneBenefit = new TypeOneBenefit(BigDecimal.TEN);
        Benefits benefits1 = new Benefits(typeOneBenefit, null);
        Product product = new FixedProduct(productId1, operator, benefits1);
        product.setDescription(test);
        productService.addProduct(product);

        TypeTwoBenefit typeTwoBenefit = new TypeTwoBenefit(BigDecimal.ONE.toString());
        Benefits benefits2 = new Benefits(null, typeTwoBenefit);
        Product product2 = new FixedProduct(productId2, operator, benefits2);
        operatorService.addProductToOperator(product2);
        // When
        ProductPK productPK = new ProductPK(productId1, operatorID, USA);
        operatorService.deleteOperator(new Operator.OperatorPK(operatorID, USA));

        // Then
        Optional<Product> byId2 = productService.getProduct(productPK);
        assertThat(byId2).isEmpty();
    }

    @Test
    void shouldDeleteAllProductsWithBenefits() {
        // Given
        String productId1 = "ID1";
        String productId2 = "ID2";
        String operatorID = "operatorID2";
        String test = "test";
        Operator operator = new Operator(operatorID);
        // operatorService.addOperator(operator);

        TypeOneBenefit typeOneBenefit = new TypeOneBenefit(BigDecimal.TEN);
        Benefits benefits1 = new Benefits(typeOneBenefit, null);
        Product product = new FixedProduct(productId1, operator, benefits1);
        product.setDescription(test);
        productService.addProduct(product);

        TypeTwoBenefit typeTwoBenefit = new TypeTwoBenefit(BigDecimal.ONE.toString());
        operator = operatorService.getOperator(operatorID).orElseThrow();
        Benefits benefits2 = new Benefits(null, typeTwoBenefit);
        Product product2 = new FixedProduct(productId2, operator, benefits2);
        operatorService.addProductToOperator(product2);
        //productService.addProduct(product2);
        // When
        productService.deleteAllProducts();
        // Then
        ProductPK productPK = new ProductPK(productId1, operatorID, USA);
        Optional<Product> byId2 = productService.getProduct(productPK);
        assertThat(byId2).isEmpty();
    }
}