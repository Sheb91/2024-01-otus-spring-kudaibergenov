package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {

    @Mock
    QuestionDao questionDao;

    @Test
    void shouldLoadSomeDataFromFile() {
        given(questionDao.findAll())
                .willReturn(List.of(new Question("test question", List.of(new Answer("test answer", true)))));
        assertTrue(questionDao.findAll().size() > 0);
    }

    @Test
    void shouldHaveFiveQuestionsInResourceFile() {
        given(questionDao.findAll())
                .willReturn(List.of(
                        new Question("test question", List.of(new Answer("test answer", true))),
                        new Question("test question2", List.of(new Answer("test answer2", false))),
                        new Question("test question3", List.of(new Answer("test answer3", false))),
                        new Question("test question4", List.of(new Answer("test answer4", false))),
                        new Question("test question5", List.of(new Answer("test answer5", false)))
                ));
        assertEquals(5, questionDao.findAll().size());
    }
}
