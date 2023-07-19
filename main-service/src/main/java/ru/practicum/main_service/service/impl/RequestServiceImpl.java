package ru.practicum.main_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.RequestStatus;
import ru.practicum.main_service.model.dto.RequestDTO;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.entity.RequestEntity;
import ru.practicum.main_service.model.entity.UserEntity;
import ru.practicum.main_service.model.mapper.RequestMapper;
import ru.practicum.main_service.repository.EventRepository;
import ru.practicum.main_service.repository.UserRepository;
import ru.practicum.main_service.service.RequestService;
import ru.practicum.main_service.repository.RequestRepository;
import ru.practicum.main_service.util.MessageResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<RequestDTO> getRequests(Long userId) {
        if (userId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        return RequestMapper.INSTANCE.toRequestDTO(requestRepository.findAllByUserId(userId));
    }

    @Transactional
    @Override
    public RequestDTO addRequest(Long userId, Long eventId) {
        if (userId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        Optional<RequestEntity> optionalRequest = requestRepository.findByUserIdAndEventId(userId, eventId);

        if (optionalRequest.isPresent() && optionalRequest.get().getStatus() != RequestStatus.CANCELED) {
            throw MessageResponseStatusException.
                    getConflictException("The request is already exists for event with id=" + eventId);
        }

        EventEntity event = eventRepository.findById(eventId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Event", eventId));
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("User", userId));

        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw MessageResponseStatusException
                    .getConflictException("Cannot add request to event hosted by requester. Value: " + userId);
        } else if (event.getState() != EventState.PUBLISHED) {
            throw MessageResponseStatusException.getConflictException("The event not published. Value: " + eventId);
        } else if (event.getParticipantLimit() <= event.getConfirmedRequests() && event.getParticipantLimit() != 0) {
            throw MessageResponseStatusException.getConflictException("The event participant limit has been reached.");
        }

        RequestStatus requestStatus = event.getRequestModeration() &&
                event.getParticipantLimit() != 0 ? RequestStatus.PENDING : RequestStatus.CONFIRMED;

        if (requestStatus == RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }

        RequestEntity request;
        if (optionalRequest.isPresent()) {
            request = optionalRequest.get();
            request.setStatus(requestStatus);
        } else {
            request = RequestMapper.INSTANCE.toRequestEntity(event, user, requestStatus);
        }

        eventRepository.save(event);
        return RequestMapper.INSTANCE.toRequestDTO(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDTO cancelRequest(Long userId, Long requestId) {
        if (userId == null || requestId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        RequestEntity request = requestRepository.findByIdAndUserId(requestId, userId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Request", requestId));

        if (request.getStatus() == RequestStatus.CANCELED) {
            throw MessageResponseStatusException.getConflictException("Cannot cancel request; " +
                    "the request has already been canceled");
        }

        EventEntity event = request.getEvent();
        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw MessageResponseStatusException
                    .getConflictException("Cannot cancel request; the event has already started");
        }

        event.setConfirmedRequests(event.getConfirmedRequests() - 1);
        eventRepository.save(event);

        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.INSTANCE.toRequestDTO(requestRepository.save(request));
    }
}
