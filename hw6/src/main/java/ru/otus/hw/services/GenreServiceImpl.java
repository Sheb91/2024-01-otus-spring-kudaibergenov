package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(genre -> genreRepository.delete(genre));
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String name) {
        if (name != null) {
            findById(id).ifPresent(genre -> {
                genre.setName(name);
                genreRepository.update(genre);
            });
        }
    }

    @Override
    @Transactional
    public void save(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }
}
