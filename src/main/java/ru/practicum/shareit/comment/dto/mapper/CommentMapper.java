package ru.practicum.shareit.comment.dto.mapper;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setCreated(comment.getCreated());
        return dto;
    }

    public static Comment toEntity(String text, Comment comment) {
        comment.setText(text);
        comment.setCreated(LocalDateTime.now());
        return comment;
    }
}
