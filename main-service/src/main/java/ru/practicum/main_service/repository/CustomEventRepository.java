package ru.practicum.main_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.model.EventState;
import ru.practicum.main_service.model.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomEventRepository {
    Page<EventEntity> getFilteredUserEvents(String search,
                                            List<Long> categories,
                                            Boolean paid,
                                            LocalDateTime startDate,
                                            LocalDateTime endDate,
                                            boolean isAvailable,
                                            Pageable pageable);

    Page<EventEntity> getFilteredAdminEvents(List<Long> users, List<EventState> states, List<Long> categories,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);
}
