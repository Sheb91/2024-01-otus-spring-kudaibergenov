package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("book_id", id);
        try {
            return Optional.of(namedParameterJdbcOperations.queryForObject(
                    "select b.id, b.title, b.author_id, a.full_name, b.genre_id, g.name from books b" +
                            " join authors a on a.id = b.author_id" +
                            " join genres g on g.id = b.genre_id" +
                            " where b.id = :book_id",
                    params,
                    new BookRowMapper()
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, b.author_id, a.full_name, b.genre_id, g.name from books b" +
                        " join authors a on a.id = b.author_id" +
                        " join genres g on g.id = b.genre_id",
                new BookRowMapper()
        );
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete from books where id = :book_id",
                Collections.singletonMap("book_id", id));
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into books(title, author_id, genre_id)" +
                        " values (:title, :authorId, :genreId)",
                new MapSqlParameterSource()
                        .addValue("title", book.getTitle())
                        .addValue("authorId", book.getAuthor().getId())
                        .addValue("genreId", book.getGenre().getId()),
                keyHolder
        );

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        int rowsUpdated = namedParameterJdbcOperations.update(
                "update books" +
                        " set title = :title" +
                        " where id = :bookId" +
                        " and author_id = :authorId" +
                        " and genre_id = :genreId",
                new MapSqlParameterSource()
                        .addValue("title", book.getTitle())
                        .addValue("bookId", book.getId())
                        .addValue("authorId", book.getAuthor().getId())
                        .addValue("genreId", book.getGenre().getId())
        );
        if (rowsUpdated == 0) {
            throw new EntityNotFoundException("Не удалось обновить книгу");
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("id");
            String bookName = rs.getString("title");

            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("full_name");

            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("name");

            return new Book(bookId, bookName, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
