package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerIdOrderByStartTimeDesc(Long bookerId);

    List<Booking> findByItemOwnerIdOrderByStartTimeDesc(Long ownerId);

    Optional<Booking> findFirstByItemIdAndStartTimeBeforeOrderByStartTimeDesc(Long itemId, LocalDateTime now);

    Optional<Booking> findFirstByItemIdAndStartTimeAfterOrderByStartTimeAsc(Long itemId, LocalDateTime now);

}
