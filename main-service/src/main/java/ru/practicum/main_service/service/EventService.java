package ru.practicum.main_service.service;

import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.SortType;
import ru.practicum.main_service.model.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<FullEventDTO> getFullEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    FullEventDTO adminEditEvent(Long eventId, UpdateEventDTO event);

    List<EventDTO> getUserEvents(Long userId, Integer from, Integer size);

    FullEventDTO addEvent(Long userId, CreatedEventDTO event);

    EventDTOWithComment getFullEvent(Long userId, Long eventId);

    FullEventDTO userEditEvent(Long userId, Long eventId, UpdateEventDTO event);

    List<RequestDTO> getEventRequests(Long userId, Long eventId);

    ConfirmedRequestsDTO confirmRequests(Long userId, Long eventId, UpdateRequestsDTO requests);

    List<EventDTO> getEvents(String text, List<Long> categories, Boolean paid,
                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                             SortType sort, Integer from, Integer size, String clientIp, String endpointPath);

    EventDTOWithComment getEvent(Long id, String clientIp, String endpointPath);

    CommentDTO addCommentToEvent(Long userId, Long eventId, CreatedCommentDTO commentDTO);

    CommentDTO editComment(Long userId, Long commentId, CreatedCommentDTO commentDTO);

    CommentDTO likeComment(Long userId, Long commentId);

    void removeComment(Long userId, Long commentId);

    ReplyDTO addReplyToComment(Long userId, Long commentId, CreatedCommentDTO replyDTO);

    ReplyDTO editReply(Long userId, Long replyId, CreatedCommentDTO commentDTO);

    ReplyDTO likeReply(Long userId, Long replyId);

    void removeReply(Long userId, Long replyId);

    List<ReplyDTO> getReplies(Long commentId, Integer from, Integer size, String clientIp, String endpointPath);

    List<CommentDTO> getComments(Long eventId, Integer from, Integer size, String clientIp, String endpointPath);
}
