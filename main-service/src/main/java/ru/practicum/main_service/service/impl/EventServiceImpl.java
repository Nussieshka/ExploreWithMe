package ru.practicum.main_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.library.Stats;
import ru.practicum.main_service.ExploreWithMe;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.RequestStatus;
import ru.practicum.main_service.model.SortType;
import ru.practicum.main_service.model.StateAction;
import ru.practicum.main_service.model.dto.*;
import ru.practicum.main_service.model.entity.CategoryEntity;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.entity.RequestEntity;
import ru.practicum.main_service.model.entity.UserEntity;
import ru.practicum.main_service.model.mapper.EventMapper;
import ru.practicum.main_service.model.mapper.EventUpdateMapper;
import ru.practicum.main_service.model.mapper.RequestMapper;
import ru.practicum.main_service.repository.CategoryRepository;
import ru.practicum.main_service.repository.RequestRepository;
import ru.practicum.main_service.repository.UserRepository;
import ru.practicum.main_service.service.EventService;
import ru.practicum.main_service.repository.EventRepository;
import ru.practicum.main_service.util.MessageResponseStatusException;
import ru.practicum.main_service.util.Util;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    private final StatsClient statsClient;

    @Override
    public List<FullEventDTO> getFullEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Integer from, Integer size) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new MessageResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Field: eventDate. Error: endDate cannot be earlier than startDate. Values: startDate: "
                            + rangeStart + ", endDate: " + rangeEnd);
        }

        return EventMapper.INSTANCE.toFullEventDTO(eventRepository.getFilteredAdminEvents(
                        users, states, categories, rangeStart, rangeEnd, PageRequest.of(from / size, size)));
    }

    @Override
    public FullEventDTO adminEditEvent(Long eventId, UpdateEventDTO event) {
        if (eventId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        Util.validateUpdateEventDTO(event);

        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> MessageResponseStatusException.getNotFoundException("Event", eventId));

        LocalDateTime now = LocalDateTime.now();
        if (eventEntity.getEventDate().minusHours(1).isBefore(now)) {
            throw MessageResponseStatusException.getConflictException(
                    "The event will start in less than an hour, or it has already started.");
        } else if (Util.isInvalidEventDate(event.getEventDate())) {
            throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Field: eventDate. Error: cannot be earlier than two hours from now. Value: "
                            + event.getEventDate());
        }

        StateAction stateAction = event.getStateAction();
        if (stateAction != null && !stateAction.getRequiresAdminPrivileges()) {
            throw MessageResponseStatusException.getConflictException(
                    "This action requires event's owner privileges. Value: " + stateAction.name());
        }
        if (stateAction == StateAction.PUBLISH_EVENT) {
            if (eventEntity.getState() != EventState.PENDING) {
                throw MessageResponseStatusException.getConflictException(
                        "Cannot publish the event because it's not in the right state: " + eventEntity.getState());
            }
            eventEntity.setState(EventState.PUBLISHED);
            eventEntity.setPublishedOn(now);
        } else if (stateAction == StateAction.REJECT_EVENT) {
            if (eventEntity.getState() == EventState.PUBLISHED) {
                throw MessageResponseStatusException.getConflictException(
                        "Cannot publish the event because it's not in the right state: " + eventEntity.getState());
            }
            eventEntity.setState(EventState.CANCELED);
        }

        return getUpdatedEvent(event, eventEntity);
    }

    @Override
    public List<EventDTO> getUserEvents(Long userId, Integer from, Integer size) {
        if (userId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }
        return EventMapper.INSTANCE.toEventDTO(
                eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size)));
    }

    @Override
    public FullEventDTO addEvent(Long userId, CreatedEventDTO event) {
        if (userId == null) {
            throw MessageResponseStatusException.getNullIdException();
        } else if (Util.isInvalidEventDate(event.getEventDate())) {
            throw new MessageResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Field: eventDate. Error: cannot be earlier than two hours from now. Value: "
                            + event.getEventDate());
        }

        Long categoryId = event.getCategory();

        CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Category", categoryId));
        UserEntity initiator = userRepository.findById(userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("User", userId));

        return EventMapper.INSTANCE.toFullEventDTO(eventRepository.save(
                EventMapper.INSTANCE.toEventEntity(event, category, initiator)));
    }

    @Override
    public FullEventDTO getFullEvent(Long userId, Long eventId) {
        if (userId == null || eventId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        EventEntity event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Event", eventId));

        return EventMapper.INSTANCE.toFullEventDTO(event);
    }

    @Override
    public FullEventDTO userEditEvent(Long userId, Long eventId, UpdateEventDTO event) {
        if (userId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        Util.validateUpdateEventDTO(event);

        StateAction stateAction = event.getStateAction();
        if (stateAction != null && stateAction.getRequiresAdminPrivileges()) {
            throw MessageResponseStatusException.getConflictException(
                    "This action requires admin privileges. Value: " + stateAction.name());
        }

        EventEntity eventEntity = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> MessageResponseStatusException.getNotFoundException("Event", eventId));

        if (eventEntity.getState() == EventState.PUBLISHED) {
            throw MessageResponseStatusException.getConflictException(
                    "Only pending or canceled events can be changed");
        } else if (Util.isInvalidEventDate(event.getEventDate())) {
            throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Field: eventDate. Error: cannot be earlier than two hours from now. Value: "
                            + event.getEventDate());
        }

        if (stateAction == StateAction.CANCEL_REVIEW) {
            eventEntity.setState(EventState.CANCELED);
        } else if (stateAction == StateAction.SEND_TO_REVIEW) {
            eventEntity.setState(EventState.PENDING);
        }

        return getUpdatedEvent(event, eventEntity);
    }

    @Override
    public List<RequestDTO> getEventRequests(Long userId, Long eventId) {
        if (userId == null || eventId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        return RequestMapper.INSTANCE.toRequestDTO(
                requestRepository.findAllByEventIdAndEventInitiatorId(eventId, userId));
    }

    @Override
    public ConfirmedRequestsDTO confirmRequests(Long userId, Long eventId, UpdateRequestsDTO requestsDTO) {
        if (userId == null || eventId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        EventEntity event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Event", eventId));

        Integer participantLimit = event.getParticipantLimit();
        Long confirmedRequestsCount = event.getConfirmedRequests();
        RequestStatus status = requestsDTO.getStatus();
        Integer requestsCount = requestsDTO.getRequestIds().size();
        Long futureConfirmedRequests = requestsCount + confirmedRequestsCount;

        if (!status.isEventOwnerAction()) {
            throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrectly made request.",
                    "Invalid requestStatus. Value: " + status);
        } else if (participantLimit == 0 || !event.getRequestModeration()) {
            throw MessageResponseStatusException.getConflictException("No confirmation of requests is required.");
        } else if (confirmedRequestsCount >= participantLimit) {
            throw MessageResponseStatusException.getConflictException("The participant limit has been reached.");
        } else if (futureConfirmedRequests > participantLimit
                && status != RequestStatus.REJECTED) {
            throw MessageResponseStatusException.getConflictException(
                    "Adding these requests would exceed the participant limit.");
        }

        List<RequestEntity> requests = requestRepository.findAllByIdInAndEventId(requestsDTO.getRequestIds(), eventId);
        setStatus(requests, status);

        List<RequestEntity> rejected;
        List<RequestEntity> confirmed;

        if (status != RequestStatus.REJECTED) {
            event.setConfirmedRequests(futureConfirmedRequests);
            if (futureConfirmedRequests >= participantLimit) {
                rejected = requestRepository.findAllByIdNotInAndEventIdAndStatus(requestsDTO.getRequestIds(),
                        eventId, RequestStatus.PENDING);
                setStatus(rejected, RequestStatus.REJECTED);
            } else {
                rejected = Collections.emptyList();
            }
            eventRepository.save(event);
            confirmed = requests;
        } else {
            confirmed = Collections.emptyList();
            rejected = requests;
        }
        ConfirmedRequestsDTO confirmedRequestsDTO = RequestMapper.INSTANCE.toConfirmedRequestsDTO(confirmed, rejected);

        requestRepository.saveAll(Stream.concat(confirmed.stream(), rejected.stream()).collect(Collectors.toList()));
        return confirmedRequestsDTO;
    }

    @Override
    public List<EventDTO> getEvents(String text, List<Long> categories,
                                    Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Boolean onlyAvailable,
                                    SortType sort, Integer from, Integer size, String clientIp, String endpointPath) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new MessageResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Field: eventDate. Error: endDate cannot be earlier than startDate. Values: startDate: "
                            + rangeStart + ", endDate: " + rangeEnd);
        }

        List<EventDTO> eventDTOS = EventMapper.INSTANCE.toEventDTO(
                eventRepository.getFilteredUserEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                        PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC,
                                sort == SortType.VIEWS ? "views" : "eventDate"))));

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        return eventDTOS;
    }

    @Transactional
    @Override
    public FullEventDTO getEvent(Long id, String clientIp, String endpointPath) {
        if (id == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        EventEntity event = eventRepository.findByIdIsAndStateIs(id, EventState.PUBLISHED).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Event", id));

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        Long currentViewsCount = event.getViews();

        List<Stats> statsList = statsClient.stats(List.of(endpointPath), true);
        if (statsList == null || statsList.isEmpty()) {
            throw new RuntimeException("Cannot get views from stats-service");
        }

        Long views = statsList.get(0).getHits();
        if (views != null && views > currentViewsCount) {
            event.setViews(views);
            eventRepository.save(event);
        }
        return EventMapper.INSTANCE.toFullEventDTO(event);
    }

    private FullEventDTO getUpdatedEvent(UpdateEventDTO event, EventEntity eventEntity) {
        Long categoryId = event.getCategory();

        CategoryEntity category = null;

        if (categoryId != null && !Objects.equals(categoryId, eventEntity.getCategory().getId())) {
            category = categoryRepository.findById(categoryId).orElseThrow(
                    () -> MessageResponseStatusException.getNotFoundException("Category", categoryId));
        }

        EventUpdateMapper.INSTANCE.updateEventEntityFromUpdateEventDTO(event, eventEntity, category);

        return EventMapper.INSTANCE.toFullEventDTO(eventRepository.save(eventEntity));
    }

    private void setStatus(List<RequestEntity> requests, RequestStatus status) {
         for (RequestEntity request : requests) {
             if (request.getStatus() != RequestStatus.PENDING) {
                 throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST,
                         "Incorrectly made request.", "Request must have status PENDING");
             }
             request.setStatus(status);
         }
    }
}
