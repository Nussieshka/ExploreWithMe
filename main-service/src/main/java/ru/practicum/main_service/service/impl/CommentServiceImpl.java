package ru.practicum.main_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.main_service.ExploreWithMe;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.dto.CommentDTO;
import ru.practicum.main_service.model.dto.CreatedCommentDTO;
import ru.practicum.main_service.model.dto.ReplyDTO;
import ru.practicum.main_service.model.entity.CommentEntity;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.entity.ReplyEntity;
import ru.practicum.main_service.model.entity.UserEntity;
import ru.practicum.main_service.model.mapper.CommentMapper;
import ru.practicum.main_service.repository.CommentRepository;
import ru.practicum.main_service.repository.EventRepository;
import ru.practicum.main_service.repository.ReplyRepository;
import ru.practicum.main_service.repository.UserRepository;
import ru.practicum.main_service.service.CommentService;
import ru.practicum.main_service.util.MessageResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    private final StatsClient statsClient;

    @Override
    public CommentDTO addCommentToEvent(Long userId, Long eventId, CreatedCommentDTO commentDTO) {
        if (userId == null || eventId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("User", userId));

        EventEntity event = eventRepository.findById(eventId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Event", eventId));

        EventState state = event.getState();
        if (event.getState() != EventState.PUBLISHED) {
            throw MessageResponseStatusException.getConflictException("The event not published. Value: " + state);
        }

        return CommentMapper.INSTANCE.toCommentDTO(
                commentRepository.save(CommentMapper.INSTANCE.toCommentEntity(commentDTO, event, user)));
    }

    @Override
    public CommentDTO editComment(Long userId, Long commentId, CreatedCommentDTO commentDTO) {
        if (userId == null || commentId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        CommentEntity comment = commentRepository.findByIdAndUserId(commentId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Comment", commentId));

        comment.setMessage(commentDTO.getMessage());
        comment.setEditedOn(LocalDateTime.now());

        return CommentMapper.INSTANCE.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public CommentDTO likeComment(Long userId, Long commentId) {
        if (userId == null || commentId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("User", userId));

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Comment", commentId));

        List<UserEntity> likes = new ArrayList<>(comment.getUserLikes());

        if (likes.remove(user)) {
            comment.setUserLikes(likes);
            return CommentMapper.INSTANCE.toCommentDTO(commentRepository.save(comment));
        }

        likes.add(user);
        comment.setUserLikes(likes);

        return CommentMapper.INSTANCE.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public void removeComment(Long userId, Long commentId) {
        if (userId == null || commentId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        CommentEntity comment = commentRepository.findByIdAndUserId(commentId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Comment", commentId));

        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public ReplyDTO addReplyToComment(Long userId, Long commentId, CreatedCommentDTO replyDTO) {
        if (userId == null || commentId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Comment", commentId));

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("User", userId));

        ReplyEntity reply = CommentMapper.INSTANCE.toReplyEntity(replyDTO, comment, user);
        comment.getReplies().add(reply);

        return CommentMapper.INSTANCE.toReplyDTO(replyRepository.save(reply));
    }

    @Override
    public ReplyDTO editReply(Long userId, Long replyId, CreatedCommentDTO commentDTO) {
        if (userId == null || replyId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        ReplyEntity reply = replyRepository.findByIdAndUserId(replyId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Reply", replyId));

        reply.setMessage(commentDTO.getMessage());
        reply.setEditedOn(LocalDateTime.now());

        return CommentMapper.INSTANCE.toReplyDTO(replyRepository.save(reply));
    }

    @Override
    public ReplyDTO likeReply(Long userId, Long replyId) {
        if (userId == null || replyId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("User", userId));

        ReplyEntity reply = replyRepository.findById(replyId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Reply", replyId));

        List<UserEntity> likes = reply.getUserLikes();

        if (likes.remove(user)) {
            return CommentMapper.INSTANCE.toReplyDTO(replyRepository.save(reply));
        }

        likes.add(user);

        return CommentMapper.INSTANCE.toReplyDTO(replyRepository.save(reply));
    }

    @Override
    public void removeReply(Long userId, Long replyId) {
        if (userId == null || replyId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        ReplyEntity reply = replyRepository.findByIdAndUserId(replyId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Reply", replyId));


        replyRepository.delete(reply);
    }

    @Override
    public List<ReplyDTO> getReplies(Long commentId, Integer from, Integer size, String clientIp, String endpointPath) {
        if (commentId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        return CommentMapper.INSTANCE.toReplyDTO(
                replyRepository.findAllByCommentIdOrderByCreatedOnAsc(commentId, PageRequest.of(from / size, size)));
    }

    @Override
    public List<CommentDTO> getComments(Long eventId, Integer from, Integer size, String clientIp, String endpointPath) {
        if (eventId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        return CommentMapper.INSTANCE.toCommentDTO(
                commentRepository.findAllByEventIdOrderByUserLikesDesc(eventId, PageRequest.of(from / size, size)));
    }
}
