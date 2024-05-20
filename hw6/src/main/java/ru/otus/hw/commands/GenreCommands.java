package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {
    private final GenreService genreService;

    @ShellMethod(value = "Find genre by id", key = "gid")
    public String findGenreById(Long id) {
        return genreService.findById(id)
                .map(Genre::toString)
                .orElse("Genre with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all genres", key = "gall")
    public String findAllGenres() {
        return genreService.findAll()
                .stream()
                .map(Genre::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete genre by id", key = "gdel")
    public void deleteGenreById(Long id) {
        genreService.deleteById(id);
    }

    @ShellMethod(value = "Update genre name by id", key = "gupd")
    public void updateGenreNameById(Long id, String name) {
        genreService.updateNameById(id, name);
    }

    @ShellMethod(value = "Insert new genre", key = "gins")
    public void insertNewGenre(String name) {
        genreService.save(name);
    }
}
