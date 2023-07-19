package ru.practicum.main_service.model.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.dto.CategoryDTO;
import ru.practicum.main_service.model.entity.CategoryEntity;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toCategoryDTO(CategoryEntity category);

    CategoryEntity toCategoryEntity(CategoryDTO categoryDTO);

    @IterableMapping(elementTargetType = CategoryDTO.class)
    List<CategoryDTO> toCategoryDTO(Iterable<CategoryEntity> categories);
}
