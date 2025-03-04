package ru.practicum.shareit.booking.model;


public enum BookingStatus {
    WAITING,     // Ожидает подтверждения владельца
    APPROVED,    // Подтверждено владельцем
    REJECTED,    // Отклонено владельцем
    CANCELED     // Отменено пользователем
}
