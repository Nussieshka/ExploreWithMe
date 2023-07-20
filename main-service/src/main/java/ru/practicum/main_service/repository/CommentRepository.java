package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main_service.model.entity.CommentEntity;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByIdAndUserId(Long id, Long userId);


    @Query("SELECT comment FROM CommentEntity comment LEFT JOIN comment.userLikes likes " +
            "WHERE comment.event.id = :eventId " +
            "GROUP BY comment ORDER BY COUNT(likes) DESC")
    Page<CommentEntity> findAllByEventIdOrderByUserLikesDesc(Long eventId, Pageable pageable);
}
