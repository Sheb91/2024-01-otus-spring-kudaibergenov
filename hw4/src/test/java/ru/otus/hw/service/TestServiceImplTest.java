package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {
    @MockBean
    LocalizedIOService mockIoService;

    @MockBean
    QuestionDao mockQuestionDao;

    @Autowired
    TestService testService;


    @Test
    void shouldHaveCorrectAnswer() {
        given(mockQuestionDao.findAll())
                .willReturn(List.of(new Question("How much is 10+10=?",
                        List.of(new Answer("20", true), new Answer("30", false)))));

        given(mockIoService.readIntForRangeWithPromptLocalized(any(Integer.class), any(Integer.class), any(String.class), any(String.class)))
                .willReturn(1);

        TestResult testResult = testService.executeTestFor(new Student("John", "Doe"));
        assertEquals(1, testResult.getRightAnswersCount());
    }
}

