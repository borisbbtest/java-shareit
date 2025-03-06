package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public class ItemMapper {
    public static ItemDto toDto(Item item, BookingDto lastBooking, BookingDto nextBooking, List<CommentDto> comments) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        // Теперь получаем ownerId из объекта User
        if (item.getOwner() != null) {
            dto.setOwnerId(item.getOwner().getId());
        }

        dto.setLastBooking(null);
        dto.setNextBooking(null);
        dto.setComments(comments);

        if (item.getRequest() != null) {
            dto.setRequestId(item.getRequest().getId());
        }

        return dto;
    }

    public static ItemDto toDto(Item item) {
        return toDto(item, null, null, List.of()); // Передаем пустые значения
    }

    public static Item toEntity(ItemDto dto, User owner, ItemRequest request) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setAvailable(dto.getAvailable());
        item.setOwner(owner); // Теперь связываем объект User напрямую
        item.setRequest(request);
        return item;
    }
}
