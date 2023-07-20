package ru.practicum.main_service.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDTOWithComment extends FullEventDTO {
    private CommentDTO topComment;
}
