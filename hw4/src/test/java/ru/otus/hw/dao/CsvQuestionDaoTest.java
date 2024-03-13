package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = CsvQuestionDao.class)
public class CsvQuestionDaoTest {
    @MockBean
    AppProperties appProperties;

    @Autowired
    QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        Mockito.when(appProperties.getTestFileName()).thenReturn("questions.csv");
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
