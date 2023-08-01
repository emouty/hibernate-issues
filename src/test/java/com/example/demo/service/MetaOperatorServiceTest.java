package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.local.MetaOperator;
import com.example.demo.local.Operator;
import com.example.demo.local.Product;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(properties = { "spring.jpa.properties.hibernate.cache.use_second_level_cache=true",
                               "spring.jpa.properties.hibernate.cache.use_query_cache=true" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class MetaOperatorServiceTest {
    @Autowired
    MetaOperatorService metaOperatorService;
    @Autowired
    OperatorService operatorService;
    @Autowired
    ProductService productService;

    @Test
    void should_get_metaOperator_from_operator_in_cache() {
        //given
        String metaId = "metaId";
        MetaOperator metaOperator = new MetaOperator(metaId);
        metaOperatorService.addMetaOperator(metaOperator);

        String operatorId = "operatorId";
        Operator operator = new Operator(operatorId);
        operatorService.addOperator(operator);

        metaOperatorService.attachOperator(metaId, operatorId, com.example.demo.local.Country.USA);
        //when/then
        assertThat(operatorService.getOperator(operatorId).orElseThrow()).isNotNull();
        assertThat(operatorService.getOperator(operatorId).orElseThrow().getMetaOperator()).isNotNull();
    }

    @Test
    void should_get_metaOperator_from_product_in_cache() {
        //given
        String metaId = "metaId";
        MetaOperator metaOperator = new MetaOperator(metaId);
        metaOperatorService.addMetaOperator(metaOperator);

        String operatorId = "operatorId";
        Operator operator = new Operator(operatorId);
        operatorService.addOperator(operator);

        metaOperatorService.attachOperator(metaId, operatorId, com.example.demo.local.Country.USA);

        String productId = "product_1";
        productService.addProduct(new Product(productId, operator));

        //when/then
        Product.ProductPK productPK = new Product.ProductPK(productId, new Operator.OperatorPK(operatorId, com.example.demo.local.Country.USA));
        assertThat(productService.getProduct(productPK).orElseThrow()).isNotNull();
        assertThat(productService.getProduct(productPK).orElseThrow().getOperator().getMetaOperator()).isNotNull();
    }
}
