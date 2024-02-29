package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс TestServiceImpl")
class TestServiceImplTest {
    private TestFileNameProvider testFileNameProvider;

    private QuestionDao questionDao;

    @DisplayName("загрузка из файла")
    @Test
    void shouldLoadSomeDataFromFile() {
        testFileNameProvider = new AppProperties("questions.csv");
        questionDao = new CsvQuestionDao(testFileNameProvider);
        assertThat(questionDao.findAll()).isNotNull();
    }
}
