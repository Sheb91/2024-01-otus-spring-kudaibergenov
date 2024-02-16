package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            ioService.printFormattedLine("Question: %s", question.text());
            int rightAnswerIndex = -1;
            for (int i = 0; i < question.answers().size(); i++) {
                ioService.printFormattedLine("Answer %s: %s", i + 1, question.answers().get(i).text());
                if (question.answers().get(i).isCorrect()) {
                    rightAnswerIndex = i + 1;
                }
            }
            int chosenAnswerToQuestion = ioService.readIntForRange(1, question.answers().size(),
                    "Такого варианта ответа не существует.");
            isAnswerValid = (rightAnswerIndex == chosenAnswerToQuestion) ? true : false;
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
