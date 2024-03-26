package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author author) {
        return null;
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
    public void updateNameById(Long id, String name) {
        findById(id).ifPresent(author -> {
            author.setName(name);
            em.merge(author);
        });
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(author -> em.remove(author));
    }

    @Override
    public void save(String name) {
        Author author = new Author();
        author.setName(name);
        em.persist(author);
    }
}
