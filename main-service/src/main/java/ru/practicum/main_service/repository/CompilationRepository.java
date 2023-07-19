package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.model.entity.CompilationEntity;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {
    Page<CompilationEntity> findAllByPinned(Boolean pinned, Pageable pageable);
}
