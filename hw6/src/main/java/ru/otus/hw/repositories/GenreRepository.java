package ru.otus.hw.repositories;

import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    void save(Genre genre);

    Optional<Genre> findById(Long id);

    List<Genre> findAll();

    void update(Genre genre);

    void delete(Long id);
}
