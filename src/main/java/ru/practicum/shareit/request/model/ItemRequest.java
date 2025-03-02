package ru.practicum.shareit.request.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ItemRequest {
    private Long id;
    private String description;
    private Long requesterId; // ID пользователя, который запрашивает вещь
    private LocalDateTime created; // Дата создания запроса
}
