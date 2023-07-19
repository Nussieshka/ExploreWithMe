package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.entity.EventEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long>, CustomEventRepository {

    Optional<EventEntity> findByIdIsAndStateIs(Long id, EventState state);

    Optional<EventEntity> findByIdAndInitiatorId(Long id, Long userId);

    Page<EventEntity> findAllByInitiatorId(Long userId, Pageable pageable);

    List<EventEntity> findAllByIdIn(Collection<Long> id);

    boolean existsByCategoryId(Long categoryId);

}
