package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.Setter;
import ru.practicum.shareit.booking.utils.BookingStatus;

import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private Long itemId;    // ID вещи
    private Long bookerId;  // ID пользователя, который бронирует
    private LocalDateTime start;
    private LocalDateTime end;
    @Setter
    private BookingStatus status;

    public BookingStatus getStatus(BookingStatus status) {
        return status;
    }

}
