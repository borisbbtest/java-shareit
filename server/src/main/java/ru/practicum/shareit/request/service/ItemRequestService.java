package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemRequestService(ItemRequestRepository requestRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * ✅ Создать запрос на вещь
     */
    public ItemRequestDto createRequest(Long userId, ItemRequestDto requestDto) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " does not exist"));

        ItemRequest request = ItemRequestMapper.toEntity(requestDto);
        request.setRequester(requester);
        request.setCreated(LocalDateTime.now());

        return ItemRequestMapper.toDto(requestRepository.save(request), List.of());
    }

    /**
     * ✅ Получить все запросы текущего пользователя (включая вещи, добавленные в ответ)
     */
    @Transactional(readOnly = true)
    public List<ItemRequestDto> getUserRequests(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " does not exist");
        }

        List<ItemRequest> requests = requestRepository.findByRequesterIdOrderByCreatedDesc(userId);
        return requests.stream()
                .map(request -> ItemRequestMapper.toDto(request, itemRepository.findByRequestId(request.getId())))
                .collect(Collectors.toList());
    }

    /**
     * ✅ Получить все запросы от других пользователей
     */
    @Transactional(readOnly = true)
    public List<ItemRequestDto> getAllRequests(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " does not exist");
        }

        List<ItemRequest> requests = requestRepository.findByRequesterIdNotOrderByCreatedDesc(userId);
        return requests.stream()
                .map(request -> ItemRequestMapper.toDto(request, itemRepository.findByRequestId(request.getId())))
                .collect(Collectors.toList());
    }

    /**
     * ✅ Получить запрос по ID (включая вещи, добавленные в ответ)
     */
    @Transactional(readOnly = true)
    public ItemRequestDto getRequestById(Long userId, Long requestId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " does not exist");
        }

        ItemRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with ID " + requestId + " not found"));

        List<Item> items = itemRepository.findByRequestId(requestId);
        return ItemRequestMapper.toDto(request, items);
    }
}
