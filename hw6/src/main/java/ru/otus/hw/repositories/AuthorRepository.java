package ru.otus.hw.repositories;

import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);

    Optional<Author> findById(Long id);

    List<Author> findAll();

    void update(Author author);

    void delete(Long id);
}
