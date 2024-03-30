package ru.otus.hw.repositories;

import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    List<Comment> findAll(Book book);

    void update(Comment comment);

    void delete(Comment comment);
}
