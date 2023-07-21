package ru.practicum.main_service.service;

import ru.practicum.main_service.model.dto.CommentDTO;
import ru.practicum.main_service.model.dto.CreatedCommentDTO;
import ru.practicum.main_service.model.dto.ReplyDTO;

import java.util.List;

public interface CommentService {

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
