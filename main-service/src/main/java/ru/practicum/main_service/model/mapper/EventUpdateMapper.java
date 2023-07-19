package ru.practicum.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.dto.UpdateEventDTO;
import ru.practicum.main_service.model.entity.CategoryEntity;
import ru.practicum.main_service.model.entity.EventEntity;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventUpdateMapper {

    EventUpdateMapper INSTANCE = Mappers.getMapper(EventUpdateMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "categoryEntity")
    @Mapping(target = "latitude",
            expression = "java(updateEventDTO.getLocation() != null ? updateEventDTO.getLocation().getLat() : eventEntity.getLatitude())")
    @Mapping(target = "longitude",
            expression = "java(updateEventDTO.getLocation() != null ? updateEventDTO.getLocation().getLon() : eventEntity.getLongitude())")
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    void updateEventEntityFromUpdateEventDTO(UpdateEventDTO updateEventDTO, @MappingTarget EventEntity eventEntity,
                                             CategoryEntity categoryEntity);
}
