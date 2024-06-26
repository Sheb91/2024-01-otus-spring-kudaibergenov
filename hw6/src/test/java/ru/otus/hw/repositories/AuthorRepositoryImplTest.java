package ru.otus.hw.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
public class AuthorRepositoryImplTest {
    private static final Long FIRST_AUTHOR_ID = 1L;

    private static final String NEW_AUTHOR_NAME = "NewUnitTestName";

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private AuthorRepositoryImpl authorRepositoryImpl;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldFindAuthorById() {
        val optionalActualAuthor = authorRepositoryImpl.findById(FIRST_AUTHOR_ID);
        val optionalExpectedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(optionalExpectedAuthor);
    }

    @Test
    void shouldSaveAuthor() {
        String authorName = "StephanTest";
        val author = new Author(null, authorName);
        authorRepositoryImpl.save(author);
        assertThat(author.getId()).isGreaterThan(0);

        val actualStudent = testEntityManager.find(Author.class, author.getId());
        assertThat(actualStudent)
                .isNotNull()
                .matches(a -> !a.getName().equals(""));
    }

    @Test
    void shouldUpdateAuthorNameById() {
        val firstAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        String authorOldName = firstAuthor.getName();

        firstAuthor.setName(NEW_AUTHOR_NAME);
        authorRepositoryImpl.update(firstAuthor);
        val updatedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(updatedAuthor.getName())
                .isNotEqualTo(authorOldName)
                .isEqualTo(NEW_AUTHOR_NAME);
    }

    @Test
    void shouldDeleteAuthor() {
        val firstAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(firstAuthor).isNotNull();

        authorRepositoryImpl.delete(FIRST_AUTHOR_ID);
        val deletedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(deletedAuthor).isNull();
    }

    @Test
    void shouldReturnCorrectAuthorList() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val authors = authorRepositoryImpl.findAll();
        assertThat(authors)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getName().equals(""));
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}
