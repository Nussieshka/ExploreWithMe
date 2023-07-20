package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main_service.model.entity.ReplyEntity;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    Optional<ReplyEntity> findByIdAndUserId(Long id, Long userId);

    Page<ReplyEntity> findAllByCommentIdOrderByCreatedOnAsc(Long commentId, Pageable pageable);
}
