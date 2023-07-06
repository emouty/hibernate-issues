package com.example.demo.service.special;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.demo.local.special.Provider.A;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.local.special.Provider;
import com.example.demo.local.special.SpecialOperator;
import com.example.demo.local.special.SpecialPricePoint;
import com.example.demo.local.special.SpecialProduct;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
                properties = { "spring.jpa.properties.hibernate.cache.use_second_level_cache=false",
                               "spring.jpa.properties.hibernate.cache.use_query_cache=false" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class SpecialProductServiceTest {

    @Autowired
    private SpecialOperatorService specialOperatorService;

    @Autowired
    private SpecialProductService specialProductService;

    @Autowired
    private SpecialPricePointService specialPricePointService;

    @Test
    void shouldAddProductToPricePoint() {
        // given
        String operatorId = "OPERATOR_1";
        Provider provider = A;
        SpecialOperator specialOperator = new SpecialOperator(provider, operatorId);
        specialOperatorService.addOperator(specialOperator);
        String wholesalePrice = "1 EUR";
        SpecialPricePoint specialPricePoint = new SpecialPricePoint(specialOperator, wholesalePrice);
        specialPricePointService.addPricePoint(specialPricePoint);
        String productId = "PRODUCT_1";
        SpecialProduct specialProduct = new SpecialProduct(productId, specialPricePoint);

        // when
        Optional<SpecialPricePoint> pricePoint = specialPricePointService.getPricePoint(provider, operatorId, wholesalePrice);
        assertThat(pricePoint).isNotEmpty();
        specialPricePointService.attachProduct(provider, operatorId, wholesalePrice, specialProduct);

        // then
        SpecialProduct product = specialProductService.getProduct(provider, operatorId, wholesalePrice, productId);
        assertThat(product.getWholesalePrice().getWholesalePrice()).isEqualTo(wholesalePrice);

    }

}
