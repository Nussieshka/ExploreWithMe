package ru.practicum.main_service.controller.registered;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CommentDTO;
import ru.practicum.main_service.model.dto.CreatedCommentDTO;
import ru.practicum.main_service.model.dto.ReplyDTO;
import ru.practicum.main_service.service.CommentService;

import javax.validation.Valid;

@RestController("privateCommentController")
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class CommentController {
    private final CommentService service;

    @PostMapping("/{eventId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @RequestBody @Valid CreatedCommentDTO commentDTO) {
        return ResponseEntity.ok(service.addCommentToEvent(userId, eventId, commentDTO));
    }

    @PatchMapping("/comments/{commentId}/edit")
    public ResponseEntity<CommentDTO> editComment(@PathVariable Long userId,
                                                  @PathVariable Long commentId,
                                                  @RequestBody @Valid CreatedCommentDTO commentDTO) {
        return ResponseEntity.ok(service.editComment(userId, commentId, commentDTO));
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<CommentDTO> likeComment(@PathVariable Long userId,
                                                  @PathVariable Long commentId) {
        return ResponseEntity.ok(service.likeComment(userId, commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> removeComment(@PathVariable Long userId,
                                                    @PathVariable Long commentId) {
        service.removeComment(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/comments/{commentId}/reply")
    public ResponseEntity<ReplyDTO> addReply(@PathVariable Long userId,
                                             @PathVariable Long commentId,
                                             @RequestBody @Valid CreatedCommentDTO commentDTO) {
        return ResponseEntity.ok(service.addReplyToComment(userId, commentId, commentDTO));
    }

    @PatchMapping("/replies/{replyId}/edit")
    public ResponseEntity<ReplyDTO> editReply(@PathVariable Long userId,
                                              @PathVariable Long replyId,
                                              @RequestBody @Valid CreatedCommentDTO commentDTO) {
        return ResponseEntity.ok(service.editReply(userId, replyId, commentDTO));
    }

    @PostMapping("/replies/{replyId}/like")
    public ResponseEntity<ReplyDTO> likeReply(@PathVariable Long userId,
                                              @PathVariable Long replyId) {
        return ResponseEntity.ok(service.likeReply(userId, replyId));
    }

    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<CommentDTO> removeReply(@PathVariable Long userId,
                                                  @PathVariable Long replyId) {
        service.removeReply(userId, replyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
