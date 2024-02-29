package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvQuestionDaoTest {
    AppProperties appProperties;
    QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        appProperties = new AppProperties();
        appProperties.setTestFileName("questions.csv");
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
