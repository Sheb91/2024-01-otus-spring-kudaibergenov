package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.config.AppProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CsvQuestionDaoTest {
    @Autowired
    AppProperties appProperties;
    QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new CsvQuestionDao(appProperties);
    }

    @Test
    void shouldLoadSomeDataFromFile() {
        assertTrue(questionDao.findAll().size() > 0);
    }

    @Test
    void shouldHaveFiveQuestionsInResourceFile() {
        assertEquals(5, questionDao.findAll().size());
    }
}
