package ru.practicum.shareit.comment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.comment.dto.CommentDto;

@Service
public class CommentClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    public CommentClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    // Добавление комментария
    public ResponseEntity<Object> addComment(Long userId, Long itemId, CommentDto commentDto) {
        String path = String.format("/%d/comment", itemId);
        return post(path, userId, commentDto);
    }

    // Получение всех комментариев к вещи
    public ResponseEntity<Object> getComments(Long itemId) {
        String path = String.format("/%d/comment", itemId);
        return get(path);
    }
}
