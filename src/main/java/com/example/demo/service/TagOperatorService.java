package com.example.demo.service;

import com.example.demo.local.TagOperator;
import com.example.demo.local.TagOperatorDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@AllArgsConstructor
public class TagOperatorService {
    TagOperatorDao tagOperatorDao;

    @Transactional(propagation = SUPPORTS)
    public Optional<TagOperator> getTagOperator(String id) {
        return tagOperatorDao.findById(id);
    }

    @Transactional(propagation = REQUIRED)
    public void addTagOperator(TagOperator tagOperator) {
        tagOperatorDao.save(tagOperator);
    }

    @Transactional(propagation = REQUIRED)
    public void deleteTagOperator(String id) {
        tagOperatorDao.deleteById(id);
    }

}
