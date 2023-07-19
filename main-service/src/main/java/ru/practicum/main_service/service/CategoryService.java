package ru.practicum.main_service.service;

import ru.practicum.main_service.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO category);

    void deleteCategory(Long catId);

    CategoryDTO patchCategory(Long catId, CategoryDTO category);

    List<CategoryDTO> getCategories(Integer from, Integer size, String clientIp, String endpointPath);

    CategoryDTO getCategory(Long catId, String clientIp, String endpointPath);
}
