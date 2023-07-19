package ru.practicum.main_service.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.SortType;
import ru.practicum.main_service.model.dto.EventDTO;
import ru.practicum.main_service.model.dto.FullEventDTO;
import ru.practicum.main_service.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController("publicEventController")
@RequestMapping("/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(@RequestParam(required = false) String text,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false) Boolean paid,
                                                    @RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeStart,
                                                    @RequestParam(required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        LocalDateTime rangeEnd,
                                                    @RequestParam(defaultValue = "False") Boolean onlyAvailable,
                                                    @RequestParam(defaultValue = "VIEWS") SortType sort,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size,
                                                    HttpServletRequest request) {
        return ResponseEntity.ok(service.getEvents(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size,
                request.getRemoteAddr(), request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullEventDTO> getEvent(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(service.getEvent(id, request.getRemoteAddr(), request.getRequestURI()));
    }
}
