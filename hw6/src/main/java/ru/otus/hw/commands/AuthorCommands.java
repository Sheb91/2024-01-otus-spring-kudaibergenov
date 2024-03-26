package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {
    private final AuthorService authorService;

    @ShellMethod(value = "Find author by id", key = "aid")
    public String findAuthorById(Long id) {
        return authorService.findById(id)
                .map(Author::toString)
                .orElse("Author with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all author", key = "aall")
    public String findAllAuthors() {
        return authorService.findAll()
                .stream()
                .map(Author::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete author by id", key = "adel")
    public void deleteAuthorById(Long id) {
        authorService.deleteById(id);
    }

    @ShellMethod(value = "Update author name by id", key = "aupd")
    public void updateAuthorNameById(Long id, String name) {
        authorService.updateNameById(id, name);
    }

    @ShellMethod(value = "Update author name by id", key = "ains")
    public void insertNewAuthor(String name) {
        authorService.save(name);
    }
}
