package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        csvQuestionDao
                .findAll()
                .forEach(question -> {
                    ioService.printFormattedLine("Question: %s", question.text());
                    int[] answersIterator = {0};
                    question.answers()
                            .forEach(answer -> {
                                ioService.printFormattedLine("Answer %s: %s", answersIterator[0], answer.text());
                                answersIterator[0]++;
                            });
                });
    }
}
