package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);

    List<Comment> findAll(Long bookId);

    void deleteById(Long id);

    void update(Long id, String description);

    Comment save(String description, Long bookId);
}
