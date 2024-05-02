package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public void delete(Long id) {
        Optional<Author> optionalAuthor = Optional.ofNullable(em.find(Author.class, id));
        optionalAuthor
            .ifPresentOrElse(
                    author -> em.remove(author),
                    () -> {
                        throw new EntityNotFoundException("Cannot delete author with id %d. Not found.".formatted(id));
                    }
            );
    }
}
