package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll(Book book) {
        EntityGraph<?> commentEntityGraph = em.getEntityGraph("comment-entity-graph");
        TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                "JOIN FETCH c.book b " +
                "JOIN FETCH b.author " +
                "JOIN FETCH b.genre " +
                "WHERE c.book = :book", Comment.class);
        query.setParameter("book", book);
        query.setHint(FETCH.getKey(), commentEntityGraph);
        return query.getResultList();
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
