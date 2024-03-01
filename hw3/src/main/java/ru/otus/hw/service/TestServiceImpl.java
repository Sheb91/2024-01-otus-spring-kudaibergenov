package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = isCorrectAnswer(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean isCorrectAnswer(Question question) {
        ioService.printFormattedLineLocalized("TestService.exact.question", question.text());
        int rightAnswerIndex = -1;
        for (int i = 0; i < question.answers().size(); i++) {
            ioService.printFormattedLineLocalized("TestService.exact.answer", i + 1, question.answers().get(i).text());
            if (question.answers().get(i).isCorrect()) {
                rightAnswerIndex = i + 1;
            }
        }
        int chosenAnswerToQuestion = ioService.readIntForRangeWithPromptLocalized(1, question.answers().size(),
                "TestService.exact.user.answer",
                "TestService.exact.user.mistake");
        return rightAnswerIndex == chosenAnswerToQuestion;
    }
}
