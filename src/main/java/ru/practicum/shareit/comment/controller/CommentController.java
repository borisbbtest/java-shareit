package ru.practicum.shareit.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/items/{itemId}/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable Long itemId,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(itemId, userId, commentDto));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long itemId) {
        return ResponseEntity.ok(commentService.getCommentsByItem(itemId));
    }
}
