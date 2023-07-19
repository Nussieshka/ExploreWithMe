package ru.practicum.main_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.main_service.ExploreWithMe;
import ru.practicum.main_service.model.dto.CategoryDTO;
import ru.practicum.main_service.model.entity.CategoryEntity;
import ru.practicum.main_service.model.mapper.CategoryMapper;
import ru.practicum.main_service.repository.EventRepository;
import ru.practicum.main_service.service.CategoryService;
import ru.practicum.main_service.repository.CategoryRepository;
import ru.practicum.main_service.util.MessageResponseStatusException;
import ru.practicum.main_service.util.Util;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final StatsClient statsClient;

    @Override
    public CategoryDTO addCategory(CategoryDTO category) {
        Long categoryId = category.getId();
        if (categoryId != null) {
            throw MessageResponseStatusException.getNonNullIdException(categoryId);
        }

        return CategoryMapper.INSTANCE.toCategoryDTO(categoryRepository.save(
                CategoryMapper.INSTANCE.toCategoryEntity(category)));
    }

    @Override
    public void deleteCategory(Long catId) {
        if (catId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }
        if (eventRepository.existsByCategoryId(catId)) {
            throw MessageResponseStatusException.getConflictException("The category is not empty");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDTO patchCategory(Long catId, CategoryDTO categoryDTO) {
        Long categoryId = categoryDTO.getId();
        if (categoryId != null) {
            throw MessageResponseStatusException.getNonNullIdException(categoryId);
        }
        if (catId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        CategoryEntity category = categoryRepository.findById(catId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Category", catId));

        Util.updateCategoryFromDTO(categoryDTO, category);

        return CategoryMapper.INSTANCE.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategories(Integer from, Integer size, String clientIp, String endpointPath) {
        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());
        return CategoryMapper.INSTANCE.toCategoryDTO(categoryRepository.findAll(PageRequest.of(from / size, size)));
    }

    @Override
    public CategoryDTO getCategory(Long catId, String clientIp, String endpointPath) {
        if (catId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        CategoryDTO categoryDTO = categoryRepository.findById(catId)
                .map(CategoryMapper.INSTANCE::toCategoryDTO).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Category", catId));

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        return categoryDTO;
    }
}
