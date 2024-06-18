package ru.otus.hw.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    private static final Long FIRST_BOOK_ID = 1L;

    private static final String NEW_BOOK_NAME = "BookTestNew";

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldFindBookById() {
        val optionalActualBook = bookRepository.findById(FIRST_BOOK_ID);
        val optionalExpectedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(optionalExpectedBook);
    }

    @Test
    void shouldSaveBook() {
        val author = testEntityManager.find(Author.class, 1L);
        val genre = testEntityManager.find(Genre.class, 1L);
        val book = new Book(null, NEW_BOOK_NAME, author, genre);
        bookRepository.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        val actualBook = testEntityManager.find(Book.class, book.getId());
        assertThat(actualBook)
                .isNotNull()
                .matches(b -> !b.getName().equals(""))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);
    }

    @Test
    void shouldUpdateBookNameById() {
        val firstBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        String bookOldName = firstBook.getName();

        firstBook.setName(NEW_BOOK_NAME);
        bookRepository.save(firstBook);
        val updatedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);

        assertThat(updatedBook.getName())
                .isNotEqualTo(bookOldName)
                .isEqualTo(NEW_BOOK_NAME);
    }

    @Test
    void shouldDeleteBook() {
        val firstBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();

        bookRepository.deleteById(FIRST_BOOK_ID);
        val deletedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

    @Test
    void shouldReturnCorrectBookList() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = bookRepository.findAll();
        assertThat(books)
                .isNotNull()
                .hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null);
    }
}
