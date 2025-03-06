package ru.practicum.shareit.request.dto.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ItemRequestMapper {

    public static ItemRequestDto toDto(ItemRequest request, List<Item> items) {
        return ItemRequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(items.stream()
                        .map(item -> ItemDto.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .description(item.getDescription())
                                .available(item.getAvailable())
                                .ownerId(item.getOwner().getId())
                                .lastBooking(null)
                                .nextBooking(null)
                                .comments(List.of())
                                .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }

    public static ItemRequest toEntity(ItemRequestDto dto) {
        ItemRequest request = new ItemRequest();
        request.setDescription(dto.getDescription());
        request.setCreated(dto.getCreated());
        return request;
    }
}
