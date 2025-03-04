package ru.practicum.shareit.booking.dto.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStartTime());
        dto.setEnd(booking.getEndTime());
        dto.setStatus(booking.getStatus());

        dto.setItem(ItemMapper.toDto(booking.getItem())); //
        dto.setBooker(UserMapper.toDto(booking.getBooker())); //

        return dto;
    }

    public static Booking toEntity(BookingDto dto, Item item, User booker) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStartTime(dto.getStart());
        booking.setEndTime(dto.getEnd());
        booking.setStatus(dto.getStatus());
        booking.setItem(item);
        booking.setBooker(booker);
        return booking;
    }
}
