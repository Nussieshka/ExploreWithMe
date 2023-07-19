package ru.practicum.main_service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.dto.FullEventDTO;
import ru.practicum.main_service.model.dto.UpdateEventDTO;
import ru.practicum.main_service.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController("adminEventController")
@RequestMapping("/admin/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<FullEventDTO>> getEvents(@RequestParam(required = false) List<Long> users,
                                                        @RequestParam(required = false) List<EventState> states,
                                                        @RequestParam(required = false) List<Long> categories,
                                                        @RequestParam(required = false)
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                            LocalDateTime rangeStart,
                                                        @RequestParam(required = false)
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                            LocalDateTime rangeEnd,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getFullEvents(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<FullEventDTO> editEvent(@PathVariable Long eventId, @RequestBody UpdateEventDTO event) {
        return ResponseEntity.ok(service.adminEditEvent(eventId, event));
    }
}
