package ru.practicum.shareit.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.service.CommentClient;
import ru.practicum.shareit.comment.dto.CommentDto;

import java.util.List;

@RestController
@RequestMapping("/items/{itemId}/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentClient commentClient;

    /**
     * Добавить комментарий к вещи
     */
    @PostMapping
    public ResponseEntity<Object> addComment(@PathVariable Long itemId,
                                             @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestBody @Valid CommentDto commentDto) {
        log.info("Adding comment for item {}, userId={}", itemId, userId);
        return commentClient.addComment(itemId, userId, commentDto);
    }

    /**
     * Получить комментарии к вещи
     */
    @GetMapping
    public ResponseEntity<Object> getComments(@PathVariable Long itemId) {
        log.info("Getting comments for item {}", itemId);
        return commentClient.getComments(itemId);
    }
}
