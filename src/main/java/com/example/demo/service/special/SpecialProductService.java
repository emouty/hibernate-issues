package com.example.demo.service.special;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.special.Provider;
import com.example.demo.local.special.SpecialOperator.SpecialOperatorPK;
import com.example.demo.local.special.SpecialPricePoint.SpecialPricePointPK;
import com.example.demo.local.special.SpecialProduct;
import com.example.demo.local.special.SpecialProduct.SpecialProductPK;
import com.example.demo.local.special.SpecialProductDao;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialProductService {

    private final SpecialProductDao specialProductDao;

    @Transactional(propagation = REQUIRED)
    public void addProduct(SpecialProduct product) {
        specialProductDao.save(product);
    }

    @Transactional(propagation = SUPPORTS)
    public SpecialProduct getProduct(Provider provider, String operatorId, String wholesalePrice, String productId) {
        return specialProductDao.findById(new SpecialProductPK(new SpecialPricePointPK(new SpecialOperatorPK(provider,
                                                                                                             operatorId),
                                                                                       wholesalePrice),
                                                               productId)).orElse(null);
    }

    public void deleteProduct(Provider provider, String operatorId, String wholesalePrice, String productId) {
        specialProductDao.deleteById(new SpecialProductPK(new SpecialPricePointPK(new SpecialOperatorPK(provider,
                                                                                                        operatorId),
                                                                                  wholesalePrice),
                                                          productId));
    }
}
