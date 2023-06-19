package com.example.demo.service;

import com.example.demo.local.tag.TagOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
class TagOperatorServiceTest {

    @Autowired
    private TagOperatorService tagOperatorService;

    @Test
    void shouldDeleteTagOperator() {
        String operatorID = "tagOperatorID";
        TagOperator operator = new TagOperator(operatorID);
        tagOperatorService.addTagOperator(operator);

        tagOperatorService.deleteTagOperator(operatorID);

        Optional<TagOperator> byId2 = tagOperatorService.getTagOperator(operatorID);
        assertThat(byId2).isEmpty();
    }
}
