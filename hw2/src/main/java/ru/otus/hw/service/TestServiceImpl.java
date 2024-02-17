package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    private boolean isCorrectAnswer(Question question) {
        ioService.printFormattedLine("Question: %s", question.text());
        int rightAnswerIndex = -1;
        for (int i = 0; i < question.answers().size(); i++) {
            ioService.printFormattedLine("Answer %s: %s", i + 1, question.answers().get(i).text());
            if (question.answers().get(i).isCorrect()) {
                rightAnswerIndex = i + 1;
            }
        }
        int chosenAnswerToQuestion = ioService.readIntForRangeWithPrompt(1, question.answers().size(),
                "Your answer is: ",
                "Error! No such option !");
        return rightAnswerIndex == chosenAnswerToQuestion;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = isCorrectAnswer(question); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
