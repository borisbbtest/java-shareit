package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Long itemId;
    private Long bookerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private ItemDto item;
    private UserDto booker;
}
