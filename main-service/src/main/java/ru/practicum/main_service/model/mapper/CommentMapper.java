package ru.practicum.main_service.model.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.dto.CommentDTO;
import ru.practicum.main_service.model.dto.CreatedCommentDTO;
import ru.practicum.main_service.model.dto.ReplyDTO;
import ru.practicum.main_service.model.entity.CommentEntity;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.entity.ReplyEntity;
import ru.practicum.main_service.model.entity.UserEntity;

import java.util.List;

@Mapper(uses = { UserMapper.class })
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "userDTO", source = "user")
    @Mapping(target = "likes", expression = "java(commentEntity.getUserLikes().size())")
    @Mapping(target = "repliesCount", expression = "java(commentEntity.getReplies().size())")
    CommentDTO toCommentDTO(CommentEntity commentEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "editedOn", ignore = true)
    @Mapping(target = "userLikes", ignore = true)
    @Mapping(target = "replies", ignore = true)
    CommentEntity toCommentEntity(CreatedCommentDTO commentDTO, EventEntity event, UserEntity user);

    @IterableMapping(elementTargetType = CommentDTO.class)
    List<CommentDTO> toCommentDTO(Iterable<CommentEntity> comments);

    @Mapping(target = "userDTO", source = "user")
    @Mapping(target = "likes", expression = "java(replyEntity.getUserLikes().size())")
    ReplyDTO toReplyDTO(ReplyEntity replyEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "editedOn", ignore = true)
    @Mapping(target = "userLikes", ignore = true)
    @Mapping(target = "message", source = "replyDTO.message")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "user", source = "user")
    ReplyEntity toReplyEntity(CreatedCommentDTO replyDTO, CommentEntity comment, UserEntity user);

    @IterableMapping(elementTargetType = ReplyDTO.class)
    List<ReplyDTO> toReplyDTO(Iterable<ReplyEntity> replies);
}
