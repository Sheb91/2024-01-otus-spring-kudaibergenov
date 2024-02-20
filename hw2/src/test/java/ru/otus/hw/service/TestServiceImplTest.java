package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    AppProperties appProperties;

    @Mock
    IOService mockIoService;

    @Mock
    QuestionDao mockQuestionDao;

    QuestionDao questionDao;

    TestService testService;

    @BeforeEach
    void setUp() {
        appProperties = new AppProperties();
        appProperties.setTestFileName("questions.csv");
        questionDao = new CsvQuestionDao(appProperties);

        testService = new TestServiceImpl(mockIoService, mockQuestionDao);
    }


    @Test
    void shouldLoadSomeDataFromFile() {
        assertTrue(questionDao.findAll().size() > 0);
    }

    @Test
    void shouldHaveFiveQuestionsInResourceFile() {
        assertEquals(5, questionDao.findAll().size());
    }

    @Test
    void shouldHaveCorrectAnswer() {
        given(mockQuestionDao.findAll())
                .willReturn(List.of(new Question("How much is 10+10=?",
                        List.of(new Answer("20", true), new Answer("30", false)))));

        given(mockIoService.readIntForRangeWithPrompt(any(Integer.class), any(Integer.class), any(String.class), any(String.class)))
                .willReturn(1);

        TestResult testResult = testService.executeTestFor(new Student("John", "Doe"));
        assertEquals(1, testResult.getRightAnswersCount());
    }
}

