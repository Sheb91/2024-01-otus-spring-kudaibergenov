package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAll(Long bookId) {
        var book = bookRepository.
                findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d is not found".formatted(bookId)));
        return commentRepository.findAll(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(comment -> commentRepository.delete(comment));
    }

    @Override
    @Transactional
    public void update(Long id, String description) {
        findById(id).ifPresent(comment -> {
            comment.setDescription(description);
            commentRepository.update(comment);
        });
    }

    @Override
    @Transactional
    public Comment save(String description, Long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d is not found".formatted(bookId)));
        var comment = new Comment();
        comment.setBook(book);
        comment.setDescription(description);
        return commentRepository.save(comment);
    }
}
