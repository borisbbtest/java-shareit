package ru.practicum.shareit.request.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    /**
     * Создать новый запрос на вещь
     */
    @PostMapping
    public ResponseEntity<ItemRequestDto> createRequest(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemRequestDto requestDto) {
        return ResponseEntity.ok(itemRequestService.createRequest(userId, requestDto));
    }

    /**
     *  Получить список своих запросов
     */
    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getUserRequests(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(itemRequestService.getUserRequests(userId));
    }

    /**
     *  Получить список всех запросов от других пользователей
     */
    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllRequests(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(itemRequestService.getAllRequests(userId));
    }

    /**
     *  Получить запрос по ID
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getRequestById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long requestId) {
        return ResponseEntity.ok(itemRequestService.getRequestById(userId, requestId));
    }
}
