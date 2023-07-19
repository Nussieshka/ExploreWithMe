package ru.practicum.main_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.model.RequestStatus;
import ru.practicum.main_service.model.entity.RequestEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    boolean existsByUserIdAndEventIdAndStatusNot(Long userId, Long eventId, RequestStatus status);

    Optional<RequestEntity> findByIdAndUserId(Long id, Long userId);

    List<RequestEntity> findAllByUserId(Long userId);

    Optional<RequestEntity> findByUserIdAndEventId(Long userId, Long eventId);

    List<RequestEntity> findAllByEventIdAndEventInitiatorId(Long eventId, Long userId);

    List<RequestEntity> findAllByIdInAndEventId(Collection<Long> id, Long eventId);

    List<RequestEntity> findAllByIdNotInAndEventIdAndStatus(Collection<Long> id, Long eventId, RequestStatus status);
}
