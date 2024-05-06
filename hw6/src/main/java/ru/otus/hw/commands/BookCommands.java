package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;

    @ShellMethod(value = "Find book by id", key = "bid")
    public String findBookById(Long id) {
        return bookService.findById(id)
                .map(Book::toString)
                .orElse("Book with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all books", key = "ball")
    public String findAllBooks() {
        return bookService.findAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void deleteBookById(Long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "Update book name by id", key = "bupd")
    public void updateBookNameById(Long id,
                                   String name,
                                   @ShellOption(defaultValue = "0") Long authorId,
                                   @ShellOption(defaultValue = "0") Long genreId) {
        bookService.update(id, name, authorId, genreId);
    }

    @ShellMethod(value = "Insert new Book", key = "bins")
    public String saveBook(String name, Long authorId, Long genreId) {
        var savedBook = bookService.save(name, authorId, genreId);
        return savedBook.toString();
    }
}
