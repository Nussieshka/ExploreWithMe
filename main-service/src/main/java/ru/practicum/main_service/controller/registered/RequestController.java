package ru.practicum.main_service.controller.registered;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.RequestDTO;
import ru.practicum.main_service.service.RequestService;

import java.util.List;

@RestController("privateRequestController")
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestController {

    private final RequestService service;

    @GetMapping
    public ResponseEntity<List<RequestDTO>> getRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getRequests(userId));
    }

    @PostMapping
    public ResponseEntity<RequestDTO> addRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addRequest(userId, eventId));
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<RequestDTO> cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return ResponseEntity.ok(service.cancelRequest(userId, requestId));
    }
}
