package ru.otus.hw.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentRepositoryImpl.class, BookRepositoryImpl.class})
public class CommentRepositoryImplTest {
    private static final Long FIRST_COMMENT_ID = 1L;

    private static final String NEW_COMMENT_DESCRIPTION = "CommentTestNew";

    private static final int EXPECTED_NUMBER_OF_COMMENTS_IN_BOOK = 3;

    private static final int EXPECTED_QUERIES_COUNT = 1;
    @Autowired
    private CommentRepositoryImpl commentRepositoryImpl;

    @Autowired
    private BookRepositoryImpl bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldFindBookById() {
        val optionalActualComment = commentRepositoryImpl.findById(FIRST_COMMENT_ID);
        val optionalExpectedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(optionalExpectedComment);
    }

    @Test
    void shouldSaveComment() {
        val book = testEntityManager.find(Book.class, 1L);
        val comment = new Comment(null, book, NEW_COMMENT_DESCRIPTION);
        commentRepositoryImpl.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        val actualComment = testEntityManager.find(Comment.class, comment.getId());
        assertThat(actualComment)
                .isNotNull()
                .matches(c -> !c.getDescription().equals(""))
                .matches(c -> c.getBook() != null);
    }

    @Test
    void shouldUpdateCommentDescriptionById() {
        val firstComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        String commentOldDescription = firstComment.getDescription();

        firstComment.setDescription(NEW_COMMENT_DESCRIPTION);
        commentRepositoryImpl.update(firstComment);
        val updatedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(updatedComment.getDescription())
                .isNotEqualTo(commentOldDescription)
                .isEqualTo(NEW_COMMENT_DESCRIPTION);
    }

    @Test
    void shouldDeleteCommentById() {
        val firstComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(firstComment).isNotNull();

        commentRepositoryImpl.delete(firstComment);
        val deletedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }

    @Test
    void shouldReturnCorrectCommentList() {
        Book book = bookRepository.findById(1L).get();

        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val comments = commentRepositoryImpl.findAll(book);
        assertThat(comments)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_COMMENTS_IN_BOOK)
                .allMatch(c -> !c.getDescription().equals(""))
                .allMatch(c -> c.getBook() != null);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}
