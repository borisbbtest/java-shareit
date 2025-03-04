package ru.practicum.shareit.booking.dto.mapper;


import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setItemId(booking.getItemId());
        dto.setBookerId(booking.getBookerId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setStatus(booking.getStatus()); // Теперь метод существует!
        return dto;
    }

    public static Booking toEntity(BookingDto dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setItemId(dto.getItemId());
        booking.setBookerId(dto.getBookerId());
        booking.setStart(dto.getStart());
        booking.setEnd(dto.getEnd());
        booking.setStatus(dto.getStatus());
        return booking;
    }
}