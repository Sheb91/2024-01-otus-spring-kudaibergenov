package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name, Long authorId, Long genreId) {
        Book book = findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Cannot update book with id %d. Not found.".formatted(id));
        });
        book.setName(name);

        Author author = authorService.findById(authorId).orElseThrow(() -> {
            throw new EntityNotFoundException("Cannot update book with authorId %d. Not found.".formatted(authorId));
        });
        book.setAuthor(author);

        Genre genre = genreService.findById(genreId).orElseThrow(() -> {
            throw new EntityNotFoundException("Cannot update book with genreId %d. Not found.".formatted(genreId));
        });
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book save(String name, Long authorId, Long genreId) {
        var author = authorService.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d is not found".formatted(authorId)));
        var genre = genreService.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d is not found".formatted(genreId)));
        var book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        return bookRepository.save(book);
    }
}
