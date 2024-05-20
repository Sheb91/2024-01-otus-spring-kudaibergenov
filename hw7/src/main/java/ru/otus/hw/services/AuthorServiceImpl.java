package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String name) {
        findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Cannot update author with id %d. Not found.".formatted(id));
        });
        authorRepository.updateNameById(id, name);
    }

    @Override
    @Transactional
    public void save(String name) {
        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
    }
}
