package ru.practicum.shareit.booking.utils;


public enum BookingStatus {
    WAITING,     // Ожидает подтверждения владельца
    APPROVED,    // Подтверждено владельцем
    REJECTED,    // Отклонено владельцем
    CANCELED     // Отменено пользователем
}
