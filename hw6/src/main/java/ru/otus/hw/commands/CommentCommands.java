package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {
    private final CommentService commentService;

    @ShellMethod(value = "Find comment by id", key = "cid")
    public String findCommentById(Long id) {
        return commentService.findById(id)
                .map(Comment::toString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find all comments by bookId", key = "call")
    public String findAllCommentsByBookId(Long bookId) {
        return commentService.findAll(bookId)
                .stream()
                .map(Comment::toString)
                .collect(Collectors.joining(", " + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteCommentById(Long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Update comment description by id", key = "cupd")
    public void updateCommentNameById(Long id, String description) {
        commentService.update(id, description);
    }

    @ShellMethod(value = "Insert new Comment", key = "cins")
    public String saveComment(String description, Long bookId) {
        var savedComment = commentService.save(description, bookId);
        return savedComment.toString();
    }
}
