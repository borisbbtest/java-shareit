package ru.practicum.shareit.booking.model;

import lombok.Data;
import ru.practicum.shareit.booking.utils.BookingStatus;
import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private Long itemId;    // ID вещи
    private Long bookerId;  // ID пользователя, который бронирует
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
}
