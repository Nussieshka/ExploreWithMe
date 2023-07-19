package ru.practicum.main_service.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;
import ru.practicum.main_service.model.entity.CompilationEntity;
import ru.practicum.main_service.model.entity.EventEntity;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompilationUpdateMapper {
    CompilationUpdateMapper INSTANCE = Mappers.getMapper(CompilationUpdateMapper.class);

    @Mapping(target = "events", source = "events")
    void updateCompilationEntityFromUpdateCompilationDTO(CreatedCompilationDTO createdCompilationDTO,
                                                         @MappingTarget CompilationEntity compilationEntity,
                                                         List<EventEntity> events);
}
