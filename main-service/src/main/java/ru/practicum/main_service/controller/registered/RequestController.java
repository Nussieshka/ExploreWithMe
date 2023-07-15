package ru.practicum.main_service.controller.registered;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.RequestDTO;

import java.util.List;

@RestController("privateRequestController")
@RequestMapping("/users/{userId}/requests")
public class RequestController {
    @GetMapping
    public ResponseEntity<List<RequestDTO>> getRequests(@PathVariable Integer userId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<RequestDTO> addRequest(@PathVariable Integer userId, @RequestParam Integer eventId) {
        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<RequestDTO> cancelRequest(@PathVariable Integer userId, @PathVariable Integer requestId) {
        return null;
    }
}
