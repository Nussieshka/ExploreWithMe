package ru.practicum.main_service.controller.registered;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.*;

import java.util.List;

@RestController("privateEventController")
@RequestMapping("/users/{userId}/events")
public class EventController {

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(@PathVariable Integer userId,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @PostMapping
    public ResponseEntity<FullEventDTO> postEvent(@PathVariable Integer userId,
                                                     @RequestBody CreatedEventDTO event) {
        return null;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<FullEventDTO> getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<FullEventDTO> editEvent(@PathVariable Integer userId,
                                            @PathVariable Integer eventId,
                                            @RequestBody UpdateEventDTO event) {
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<RequestDTO>> getRequest(@PathVariable Integer userId,
                                             @PathVariable Integer eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<ConfirmedRequestsDTO> editRequest(@PathVariable Integer userId,
                                              @PathVariable Integer eventId,
                                              @RequestBody UpdateRequestsDTO requests) {
        return null;
    }
}
