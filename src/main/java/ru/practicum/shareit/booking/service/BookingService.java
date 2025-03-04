package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.DuplicateException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public BookingDto createBooking(BookingDto bookingDto, Long userId) {
        var booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        var item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Item not found"));

        // 🔹 Проверка: нельзя бронировать недоступную вещь
        if (!item.getAvailable()) {
            throw new IllegalStateException("Item is not available for booking");
        }

        // 🔹 Проверка: нельзя бронировать свою же вещь
        if (item.getOwner().getId().equals(userId)) {
            throw new IllegalStateException("You cannot book your own item");
        }

        // 🔹 Проверка: даты начала и окончания бронирования
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new IllegalArgumentException("Start and end time must be provided");
        }
        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
//        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
//            throw new IllegalArgumentException("Start time cannot be in the past");
//        }

        var booking = BookingMapper.toEntity(bookingDto, item, booker);
        booking.setStatus(BookingStatus.WAITING);
        return BookingMapper.toDto(bookingRepository.save(booking));
    }



    public BookingDto approveBooking(Long bookingId, Boolean approved, Long ownerId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DuplicateException("Booking not found"));

        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new DuplicateException("Only the owner can approve the booking");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    public BookingDto getBookingById(Long bookingId, Long userId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DuplicateException("Booking not found"));

        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new DuplicateException("Access denied");
        }

        return BookingMapper.toDto(booking);
    }

    public List<BookingDto> getUserBookings(Long userId, String state) {
        return filterBookings(bookingRepository.findByBookerIdOrderByStartTimeDesc(userId), state);
    }

    public List<BookingDto> getOwnerBookings(Long ownerId, String state) {
        // Проверяем, существует ли владелец
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("User with ID " + ownerId + " not found");
        }

        List<Booking> bookings = bookingRepository.findByItemOwnerIdOrderByStartTimeDesc(ownerId);

        // Если у владельца нет бронирований, выбрасываем 404
        if (bookings.isEmpty()) {
            throw new NotFoundException("No bookings found for owner with ID: " + ownerId);
        }

        return filterBookings(bookings, state);
    }

    private List<BookingDto> filterBookings(List<Booking> bookings, String state) {
        return bookings.stream()
                .filter(booking -> state.equals("ALL") || booking.getStatus().name().equalsIgnoreCase(state))
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
