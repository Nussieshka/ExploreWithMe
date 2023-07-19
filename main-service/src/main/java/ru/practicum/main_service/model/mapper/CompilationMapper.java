package ru.practicum.main_service.model.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.dto.CompilationDTO;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;
import ru.practicum.main_service.model.entity.CompilationEntity;
import ru.practicum.main_service.model.entity.EventEntity;

import java.util.List;

@Mapper(uses = EventMapper.class)
public interface CompilationMapper {
    CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

    CompilationDTO toCompilationDTO(CompilationEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    CompilationEntity toCompilationEntity(CreatedCompilationDTO compilationDTO, List<EventEntity> events);

    @IterableMapping(elementTargetType = CompilationDTO.class)
    List<CompilationDTO> toCompilationDTO(Iterable<CompilationEntity> compilations);
}
