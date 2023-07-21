package ru.practicum.main_service.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CommentDTO;
import ru.practicum.main_service.model.dto.ReplyDTO;
import ru.practicum.main_service.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("publicCommentController")
@RequestMapping("/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentController {

    private final CommentService service;

    @GetMapping("/{eventId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long eventId,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "10") Integer size,
                                                        HttpServletRequest request) {
        return ResponseEntity.ok(service.getComments(eventId, from, size,
                request.getRemoteAddr(), request.getRequestURI()));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<List<ReplyDTO>> getReplies(@PathVariable Long commentId,
                                                     @RequestParam(defaultValue = "0") Integer from,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     HttpServletRequest request) {
        return ResponseEntity.ok(service.getReplies(commentId, from, size,
                request.getRemoteAddr(), request.getRequestURI()));
    }
}
