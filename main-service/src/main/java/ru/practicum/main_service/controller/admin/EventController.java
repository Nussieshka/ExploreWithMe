package ru.practicum.main_service.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CreatedEventDTO;
import ru.practicum.main_service.model.dto.FullEventDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController("adminEventController")
@RequestMapping("/admin/events")
public class EventController {
    @GetMapping
    public ResponseEntity<List<FullEventDTO>> getEvents(@RequestParam(required = false) List<Integer> users,
                                                        @RequestParam(required = false) List<String> states,
                                                        @RequestParam(required = false) List<Integer> categories,
                                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<FullEventDTO> editEvent(@PathVariable String eventId, @RequestBody CreatedEventDTO event) {
        return null;
    }
}
