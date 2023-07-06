package com.example.demo.service.special;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.local.special.SpecialOperator;
import com.example.demo.local.special.SpecialOperatorDao;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialOperatorService {

    private final SpecialOperatorDao specialOperatorDao;

    @Transactional(propagation = REQUIRED)
    public void addOperator(SpecialOperator specialOperator) {
        specialOperatorDao.save(specialOperator);
    }

}
