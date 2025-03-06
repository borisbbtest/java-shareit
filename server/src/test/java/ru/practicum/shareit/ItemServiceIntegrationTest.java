package ru.practicum.shareit;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemServiceIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByOwnerId() {
        User user = userRepository.save(new User(null, "Иван", "ivan@example.com"));
        Item item1 = itemRepository.save(Item.builder()
                .name("Ноутбук")
                .description("Игровой ноутбук")
                .available(true)
                .owner(user)
                .bookings(List.of()) // Пустой список бронирований
                .request(null)
                .build());

        Item item2 = itemRepository.save(Item.builder()
                .name("Мышка")
                .description("Игровая мышь")
                .available(true)
                .owner(user)
                .bookings(List.of()) // Пустой список бронирований
                .request(null)
                .build());


        List<Item> items = itemRepository.findByOwnerId(user.getId());
        assertThat(items).hasSize(2);
        assertThat(items).extracting(Item::getName).containsExactlyInAnyOrder("Ноутбук", "Мышка");
    }
}
