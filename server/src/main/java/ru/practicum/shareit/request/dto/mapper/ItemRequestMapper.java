package ru.practicum.shareit.request.dto.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ItemRequestMapper {

    public static ItemRequestDto toDto(ItemRequest request, List<Item> items) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());
        dto.setCreated(request.getCreated());

        // ✅ Используем конструктор без аргументов + сеттеры
        List<ItemDto> itemDtos = items.stream()
                .map(item -> {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setId(item.getId());
                    itemDto.setName(item.getName());
                    itemDto.setDescription(item.getDescription());
                    itemDto.setAvailable(item.getAvailable());
                    itemDto.setOwnerId(item.getOwner().getId());
                    itemDto.setLastBooking(null);
                    itemDto.setNextBooking(null);
                    itemDto.setComments(List.of()); // пустой список
                    return itemDto;
                })
                .collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }

    public static ItemRequest toEntity(ItemRequestDto dto) {
        ItemRequest request = new ItemRequest();
        request.setDescription(dto.getDescription());
        request.setCreated(dto.getCreated());
        return request;
    }
}
