package ru.practicum.main_service.controller.unauthorized;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.SortType;
import ru.practicum.main_service.model.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController("publicEventController")
@RequestMapping("/events")
public class EventController {
    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(@RequestParam(required = false) String text,
                                                    @RequestParam(required = false) List<Integer> categories,
                                                    @RequestParam(required = false) Boolean paid,
                                                    @RequestParam(required = false) LocalDateTime rangeStart,
                                                    @RequestParam(required = false) LocalDateTime rangeEnd,
                                                    @RequestParam(defaultValue = "False") Boolean onlyAvailable,
                                                    @RequestParam(required = false) SortType sort,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable String id) {
        return null;
    }
}
