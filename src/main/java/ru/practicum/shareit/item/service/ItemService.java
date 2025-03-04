package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.mapper.CommentMapper;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository,
                       BookingRepository bookingRepository, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    // ✅ Создание предмета
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " does not exist"));

        Item item = ItemMapper.toEntity(itemDto, owner);
        item = itemRepository.save(item);

        return ItemMapper.toDto(item, null, null, List.of());
    }

    // ✅ Обновление предмета
    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Only the owner can edit the item");
        }

        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());

        item = itemRepository.save(item);
        return ItemMapper.toDto(item, null, null, List.of());
    }

    // ✅ Получение предмета по ID
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        // Получаем последнее и следующее бронирование
        BookingDto lastBooking = bookingRepository.findFirstByItemIdAndStartTimeBeforeOrderByStartTimeDesc(itemId, LocalDateTime.now())
                .map(BookingMapper::toDto)
                .orElse(null);

        BookingDto nextBooking = bookingRepository.findFirstByItemIdAndStartTimeAfterOrderByStartTimeAsc(itemId, LocalDateTime.now())
                .map(BookingMapper::toDto)
                .orElse(null);

        // Получаем комментарии
        List<CommentDto> comments = commentRepository.findByItemId(itemId).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());

        return ItemMapper.toDto(item, lastBooking, nextBooking, comments);
    }

    // ✅ Получение всех предметов владельца
    public List<ItemDto> getAllItemsByOwner(Long userId) {
        return itemRepository.findByOwnerId(userId).stream()
                .map(item -> {
                    BookingDto lastBooking = bookingRepository.findFirstByItemIdAndStartTimeBeforeOrderByStartTimeDesc(item.getId(), LocalDateTime.now())
                            .map(BookingMapper::toDto)
                            .orElse(null);
                    BookingDto nextBooking = bookingRepository.findFirstByItemIdAndStartTimeAfterOrderByStartTimeAsc(item.getId(), LocalDateTime.now())
                            .map(BookingMapper::toDto)
                            .orElse(null);
                    List<CommentDto> comments = commentRepository.findByItemId(item.getId()).stream()
                            .map(CommentMapper::toDto)
                            .collect(Collectors.toList());
                    return ItemMapper.toDto(item, lastBooking, nextBooking, comments);
                })
                .collect(Collectors.toList());
    }

    // ✅ Поиск предметов по названию или описанию
    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return itemRepository.searchByNameOrDescription(text.toLowerCase()).stream()
                .filter(Item::getAvailable) // Фильтруем только доступные для аренды вещи
                .map(item -> {
                    List<CommentDto> comments = commentRepository.findByItemId(item.getId()).stream()
                            .map(CommentMapper::toDto)
                            .collect(Collectors.toList());
                    return ItemMapper.toDto(item, null, null, comments);
                })
                .collect(Collectors.toList());
    }
}
