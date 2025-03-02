package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final Map<Long, Item> items = new HashMap<>();
    private final UserService userService; // Добавляем зависимость от UserService
    private long nextId = 1;

    public ItemService(UserService userService) {
        this.userService = userService;
    }

    public ItemDto createItem(ItemDto itemDto, Long userId) {
        // Проверяем, существует ли пользователь
        if (userService.getUserById(userId).isEmpty()) {
            throw new NotFoundException("User with ID " + userId + " does not exist");
        }

        Item item = ItemMapper.toEntity(itemDto);
        item.setId(nextId++);
        item.setOwnerId(userId);
        items.put(item.getId(), item);
        return ItemMapper.toDto(item);
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long userId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new RuntimeException("Item not found");
        }
        if (!item.getOwnerId().equals(userId)) {
            throw new NotFoundException("Only the owner can edit the item");
        }
        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return ItemMapper.toDto(item);
    }

    public ItemDto getItemById(Long itemId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new RuntimeException("Item not found");
        }
        return ItemMapper.toDto(item);
    }

    public List<ItemDto> getAllItemsByOwner(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(userId))
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        String lowerText = text.toLowerCase();
        return items.values().stream()
                .filter(item -> (item.getName().toLowerCase().contains(lowerText) ||
                        item.getDescription().toLowerCase().contains(lowerText)) && item.getAvailable())
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }
}
