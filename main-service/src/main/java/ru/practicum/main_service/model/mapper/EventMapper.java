package ru.practicum.main_service.model.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.Location;
import ru.practicum.main_service.model.dto.*;
import ru.practicum.main_service.model.entity.CategoryEntity;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.entity.UserEntity;

import java.util.List;

@Mapper(uses = { CategoryMapper.class, UserMapper.class }, imports = Location.class)
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "location", expression = "java(new Location(event.getLatitude(), event.getLongitude()))")
    FullEventDTO toFullEventDTO(EventEntity event);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "createdOn", source = "event.createdOn")
    @Mapping(target = "location", expression = "java(new Location(event.getLatitude(), event.getLongitude()))")
    @Mapping(target = "topComment", source = "comment")
    EventDTOWithComment toCommentEventDTO(EventEntity event, CommentDTO comment);

    EventDTO toEventDTO(EventEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "category", source = "categoryEntity")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "latitude", expression = "java(eventDTO.getLocation().getLat())")
    @Mapping(target = "longitude", expression = "java(eventDTO.getLocation().getLon())")
    EventEntity toEventEntity(CreatedEventDTO eventDTO, CategoryEntity categoryEntity, UserEntity initiator);

    @IterableMapping(elementTargetType = FullEventDTO.class)
    List<FullEventDTO> toFullEventDTO(Iterable<EventEntity> eventEntities);

    @IterableMapping(elementTargetType = EventDTO.class)
    List<EventDTO> toEventDTO(Iterable<EventEntity> eventEntities);
}
