package com.example.demo.service.special;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.special.Provider;
import com.example.demo.local.special.SpecialOperator.SpecialOperatorPK;
import com.example.demo.local.special.SpecialPricePoint;
import com.example.demo.local.special.SpecialPricePoint.SpecialPricePointPK;
import com.example.demo.local.special.SpecialPricePointDao;
import com.example.demo.local.special.SpecialProduct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialPricePointService {

    private final SpecialPricePointDao pricePointDao;

    @Transactional(propagation = REQUIRED)
    public void addPricePoint(SpecialPricePoint specialPricePoint) {
        pricePointDao.save(specialPricePoint);
    }

    @Transactional(propagation = REQUIRED)
    public void attachProduct(Provider provider, String operatorId, String wholesalePrice, SpecialProduct product) {
        getPricePoint(provider, operatorId, wholesalePrice).orElseThrow().setProduct(product);
    }

    @Transactional(propagation = SUPPORTS)
    public Optional<SpecialPricePoint> getPricePoint(Provider provider, String operatorId, String wholesalePrice) {
        return pricePointDao.findById(new SpecialPricePointPK(new SpecialOperatorPK(provider,
                                                                                    operatorId),
                                                              wholesalePrice));
    }

    @Transactional(propagation = REQUIRED)
    public void deletePricePoint(Provider provider, String operatorId, String wholesalePrice) {
        pricePointDao.deleteById(new SpecialPricePointPK(new SpecialOperatorPK(provider,
                                                                               operatorId),
                                                         wholesalePrice));
    }

}
