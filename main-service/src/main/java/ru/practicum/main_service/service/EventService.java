package ru.practicum.main_service.service;

import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.SortType;
import ru.practicum.main_service.model.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<FullEventDTO> getFullEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    FullEventDTO adminEditEvent(Long eventId, UpdateEventDTO event);

    List<EventDTO> getUserEvents(Long userId, Integer from, Integer size);

    FullEventDTO addEvent(Long userId, CreatedEventDTO event);

    FullEventDTO getFullEvent(Long userId, Long eventId);

    FullEventDTO userEditEvent(Long userId, Long eventId, UpdateEventDTO event);

    List<RequestDTO> getEventRequests(Long userId, Long eventId);

    ConfirmedRequestsDTO confirmRequests(Long userId, Long eventId, UpdateRequestsDTO requests);

    List<EventDTO> getEvents(String text, List<Long> categories, Boolean paid,
                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                             SortType sort, Integer from, Integer size, String clientIp, String endpointPath);

    FullEventDTO getEvent(Long id, String clientIp, String endpointPath);
}
