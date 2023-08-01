package com.example.demo.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.Country;
import com.example.demo.local.MetaOperator;
import com.example.demo.local.MetaOperatorDao;
import com.example.demo.local.Operator;
import com.example.demo.local.OperatorDao;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MetaOperatorService {
    private final MetaOperatorDao metaOperatorDao;
    private final OperatorDao operatorDao;

    @Transactional(propagation = REQUIRED)
    public void addMetaOperator(MetaOperator operator) {
        metaOperatorDao.save(operator);
    }

    @Transactional(propagation = REQUIRED)
    public Optional<MetaOperator> getMetaOperator(String id) {
        return metaOperatorDao.findById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void attachOperator(String metaOperatorId, String operatorId, Country country) {
        MetaOperator metaOperator = metaOperatorDao.findById(metaOperatorId).orElseThrow();

        Operator operator = operatorDao.findById(new Operator.OperatorPK(operatorId, country)).orElseThrow();

        metaOperator.addOperator(operator);
    }

}
