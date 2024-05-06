package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        return em.merge(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(findBookById(id));
    }

    private Book findBookById(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        Map<String, Object> hintsMap = new HashMap<>();
        hintsMap.put(FETCH.getKey(), entityGraph);
        return em.find(Book.class, id, hintsMap);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void delete(Long id) {
        Book book = findBookById(id);
        if (book != null) {
            em.remove(book);
        } else {
            throw new EntityNotFoundException("Cannot delete book with id %d. Not found.".formatted(id));
        }
    }
}
