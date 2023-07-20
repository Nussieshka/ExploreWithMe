package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main_service.model.entity.ReplyEntity;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    Optional<ReplyEntity> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT reply FROM ReplyEntity reply LEFT JOIN reply.userLikes likes " +
            "WHERE reply.comment.id = :commentId " +
            "GROUP BY reply ORDER BY COUNT(likes) DESC")
    Page<ReplyEntity> findAllByCommentIdOrderByUserLikes(Long commentId, Pageable pageable);

    Page<ReplyEntity> findAllByCommentIdOrderByCreatedOnAsc(Long commentId, Pageable pageable);
}
