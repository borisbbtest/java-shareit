package ru.practicum.shareit.request.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.service.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;


@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    /**
     * Создать новый запрос на вещь
     */
    @PostMapping
    public ResponseEntity<Object> createRequest(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Valid @RequestBody ItemRequestDto requestDto) {
        log.info("Creating new item request from user {}", userId);
        return itemRequestClient.createRequest(userId, requestDto);
    }

    /**
     * Получить список своих запросов
     */
    @GetMapping
    public ResponseEntity<Object> getUserRequests(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting item requests for user {}", userId);
        return itemRequestClient.getUserRequests(userId);
    }

    /**
     * Получить список всех запросов от других пользователей
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting all item requests for user {}", userId);
        return itemRequestClient.getAllRequests(userId);
    }

    /**
     * Получить запрос по ID
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long requestId) {
        log.info("Getting item request {} for user {}", requestId, userId);
        return itemRequestClient.getRequestById(userId, requestId);
    }
}
