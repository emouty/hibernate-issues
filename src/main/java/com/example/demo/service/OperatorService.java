package com.example.demo.service;

import com.example.demo.local.Operator;
import com.example.demo.local.OperatorDao;
import com.example.demo.local.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

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
        return operatorDao.findById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void updateProductFromOperator(String operatorID, String productId, String description) {
        Product product1 = operatorDao.findById(operatorID).orElseThrow().getProducts().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst().orElseThrow();
        product1.setDescription(description);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteAllOperators() {
        operatorDao.deleteAll();
    }

    @Transactional(propagation = REQUIRED)
    public void deleteOperator(String id) {
        operatorDao.deleteById(id);
    }
}
