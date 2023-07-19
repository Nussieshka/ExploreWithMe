package ru.practicum.main_service.model.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.RequestStatus;
import ru.practicum.main_service.model.dto.ConfirmedRequestsDTO;
import ru.practicum.main_service.model.dto.RequestDTO;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.entity.RequestEntity;
import ru.practicum.main_service.model.entity.UserEntity;

import java.util.List;

@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    RequestEntity toRequestEntity(EventEntity event, UserEntity user, RequestStatus status);

    @Mapping(target = "event", expression = "java(request.getEvent().getId())")
    @Mapping(target = "requester", expression = "java(request.getUser().getId())")
    RequestDTO toRequestDTO(RequestEntity request);

    @Mapping(target = "confirmedRequests", source = "confirmedRequests", qualifiedByName = "requestEntityToDTO")
    @Mapping(target = "rejectedRequests", source = "rejectedRequests", qualifiedByName = "requestEntityToDTO")
    ConfirmedRequestsDTO toConfirmedRequestsDTO(List<RequestEntity> confirmedRequests,
                                                List<RequestEntity> rejectedRequests);

    @Named("requestEntityToDTO")
    @IterableMapping(elementTargetType = RequestDTO.class)
    List<RequestDTO> toRequestDTO(Iterable<RequestEntity> requests);
}
