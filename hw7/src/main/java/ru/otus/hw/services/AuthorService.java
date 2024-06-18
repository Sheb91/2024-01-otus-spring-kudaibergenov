package ru.otus.hw.services;

import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findById(Long id);

    List<Author> findAll();

    void deleteById(Long id);

    void updateNameById(Long id, String name);

    void save(String name);
}
