package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
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
        findById(id).ifPresent(book -> bookRepository.delete(book));
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        findById(id).ifPresent(book -> {
            book.setName(name);
            bookRepository.update(book);
        });
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
