package ru.practicum.main_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.model.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
