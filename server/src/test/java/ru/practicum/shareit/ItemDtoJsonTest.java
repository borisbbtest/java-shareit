package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemDtoJsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerializeItemDto() throws Exception {
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Ноутбук")
                .description("Игровой ноутбук")
                .available(true)
                .ownerId(10L)
                .build();

        String json = objectMapper.writeValueAsString(itemDto);
        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"name\":\"Ноутбук\"");
        assertThat(json).contains("\"description\":\"Игровой ноутбук\"");
        assertThat(json).contains("\"available\":true");
        assertThat(json).contains("\"ownerId\":10");
    }

    @Test
    void testDeserializeItemDto() throws Exception {
        String json = "{\"id\":1,\"name\":\"Ноутбук\",\"description\":\"Игровой ноутбук\",\"available\":true,\"ownerId\":10}";
        ItemDto itemDto = objectMapper.readValue(json, ItemDto.class);
        assertThat(itemDto.getId()).isEqualTo(1L);
        assertThat(itemDto.getName()).isEqualTo("Ноутбук");
        assertThat(itemDto.getDescription()).isEqualTo("Игровой ноутбук");
        assertThat(itemDto.getAvailable()).isTrue();
        assertThat(itemDto.getOwnerId()).isEqualTo(10L);
    }
}
