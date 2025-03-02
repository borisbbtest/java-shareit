package ru.practicum.shareit.booking.serivce;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.utils.BookingStatus;

import java.util.*;

@Service
public class BookingService {
    private final Map<Long, Booking> bookings = new HashMap<>();
    private long nextId = 1;

    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = BookingMapper.toEntity(bookingDto);
        booking.setId(nextId++);
        booking.setStatus(BookingStatus.WAITING); // Новые бронирования всегда в статусе "WAITING"
        bookings.put(booking.getId(), booking);
        return BookingMapper.toDto(booking);
    }

    public List<BookingDto> getAllBookings() {
        return bookings.values().stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    public Optional<BookingDto> getBookingById(Long id) {
        return Optional.ofNullable(bookings.get(id)).map(BookingMapper::toDto);
    }

    public BookingDto approveBooking(Long id, boolean approved) {
        Booking booking = bookings.get(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return BookingMapper.toDto(booking);
    }

    public void cancelBooking(Long id) {
        Booking booking = bookings.get(id);
        if (booking != null) {
            booking.setStatus(BookingStatus.CANCELED);
        }
    }
}
