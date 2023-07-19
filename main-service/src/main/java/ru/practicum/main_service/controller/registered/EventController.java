package ru.practicum.main_service.controller.registered;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.*;
import ru.practicum.main_service.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController("privateEventController")
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(@PathVariable Long userId,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getUserEvents(userId, from, size));
    }

    @PostMapping
    public ResponseEntity<FullEventDTO> addEvent(@PathVariable Long userId,
                                                 @RequestBody @Valid CreatedEventDTO event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addEvent(userId, event));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<FullEventDTO> getEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getFullEvent(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<FullEventDTO> editEvent(@PathVariable Long userId,
                                            @PathVariable Long eventId,
                                            @RequestBody UpdateEventDTO event) {
        return ResponseEntity.ok(service.userEditEvent(userId, eventId, event));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<RequestDTO>> getRequest(@PathVariable Long userId,
                                             @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getEventRequests(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<ConfirmedRequestsDTO> editRequest(@PathVariable Long userId,
                                              @PathVariable Long eventId,
                                              @RequestBody @Valid UpdateRequestsDTO requests) {
        return ResponseEntity.ok(service.confirmRequests(userId, eventId, requests));
    }
}
