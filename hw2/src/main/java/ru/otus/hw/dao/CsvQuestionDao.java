package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class CsvQuestionDao implements QuestionDao {
    private static final int SKIP_LINES = 1;

    private static final char SEPARATOR_QUESTION_MARK = '?';

    private static final char SEPARATOR_SEMICOLON = ';';

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try (InputStream is =
                     CsvQuestionDao.class.getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName())) {
            Objects.requireNonNull(is);
            List<QuestionDto> questionDtoList = new CsvToBeanBuilder(new InputStreamReader(is))
                    .withType(QuestionDto.class)
                    .withSeparator(SEPARATOR_QUESTION_MARK)
                    .withSeparator(SEPARATOR_SEMICOLON)
                    .withSkipLines(SKIP_LINES)
                    .build()
                    .parse();
            return questionDtoList
                    .stream()
                    .map(e -> e.toDomainObject())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
