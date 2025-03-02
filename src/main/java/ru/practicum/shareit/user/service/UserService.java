package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import java.util.*;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

    public UserDto createUser(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + userDto.getEmail());
        }
        User user = UserMapper.toEntity(userDto);
        user.setId(nextId++); // Генерируем ID
        users.put(user.getId(), user);
        return UserMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return users.values().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public Optional<UserDto> getUserById(Long id) {
        return Optional.ofNullable(users.get(id)).map(UserMapper::toDto);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = users.get(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Проверяем уникальность email
        if (userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail()) && emailExists(userDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + userDto.getEmail());
        }

        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        return UserMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }

    private boolean emailExists(String email) {
        return users.values().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
}
