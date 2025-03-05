package ru.practicum.shareit;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void createUserAndGetById() {
        UserDto userDto = new UserDto(null, "John Doe", "john@example.com");
        UserDto createdUser = userService.createUser(userDto);

        assertNotNull(createdUser.getId());
        assertEquals("John Doe", createdUser.getName());

        UserDto foundUser = userService.getUserById(createdUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertEquals(createdUser.getId(), foundUser.getId());
    }
}
