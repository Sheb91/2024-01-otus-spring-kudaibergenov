package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    void deleteById(Long id);

    void update(Long id, String name, Long authorId, Long genreId);

    Book save(String name, Long authorId, Long genreId);
}
