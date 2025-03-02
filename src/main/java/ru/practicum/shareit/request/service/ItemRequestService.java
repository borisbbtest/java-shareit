package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ItemRequestService {
    private final Map<Long, ItemRequest> requests = new HashMap<>();
    private long nextId = 1;

    public ItemRequestDto createRequest(ItemRequestDto requestDto) {
        ItemRequest request = ItemRequestMapper.toEntity(requestDto);
        request.setId(nextId++);
        request.setCreated(LocalDateTime.now()); // Устанавливаем текущее время
        requests.put(request.getId(), request);
        return ItemRequestMapper.toDto(request);
    }

    public List<ItemRequestDto> getAllRequests() {
        return requests.values().stream()
                .map(ItemRequestMapper::toDto)
                .toList();
    }

    public Optional<ItemRequestDto> getRequestById(Long id) {
        return Optional.ofNullable(requests.get(id)).map(ItemRequestMapper::toDto);
    }

    public void deleteRequest(Long id) {
        requests.remove(id);
    }
}
