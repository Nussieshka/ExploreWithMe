package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.model.entity.UserEntity;

import java.util.Collection;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> getAllByIdIn(Collection<Long> id, Pageable pageable);
}
