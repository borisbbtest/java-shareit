package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.utils.BookingStatus;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Long itemId;
    private Long bookerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status; // Добавили поле статус
}