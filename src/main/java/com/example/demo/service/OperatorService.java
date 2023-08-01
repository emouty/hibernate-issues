package com.example.demo.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static com.example.demo.local.Country.USA;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.Operator;
import com.example.demo.local.OperatorDao;
import com.example.demo.local.Product;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperatorService {

    private final OperatorDao operatorDao;

    @Transactional(propagation = REQUIRED)
    public void addOperator(Operator operator) {
        operatorDao.save(operator);
    }

    @Transactional(propagation = REQUIRED)
    public Optional<Operator> getOperator(String id) {
        return operatorDao.findById(new Operator.OperatorPK(id, USA));
    }

    @Transactional(propagation = REQUIRED)
    public void updateProductFromOperator(String operatorID, String productId, String description) {
        Product product1 = operatorDao.findById(new Operator.OperatorPK(operatorID, USA))
                                      .orElseThrow()
                                      .getProducts()
                                      .stream()
                                      .filter(p -> p.getProductId().equals(productId))
                                      .findFirst()
                                      .orElseThrow();
        product1.setDescription(description);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteAllOperators() {
        operatorDao.deleteAll();
    }

    @Transactional(propagation = REQUIRED)
    public void deleteOperator(Operator.OperatorPK id) {
        operatorDao.deleteById(id);
    }
}
