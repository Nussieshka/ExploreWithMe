package ru.practicum.main_service.service;

import ru.practicum.main_service.model.dto.RequestDTO;

import java.util.List;

public interface RequestService {
    List<RequestDTO> getRequests(Long userId);

    RequestDTO addRequest(Long userId, Long eventId);

    RequestDTO cancelRequest(Long userId, Long requestId);
}
