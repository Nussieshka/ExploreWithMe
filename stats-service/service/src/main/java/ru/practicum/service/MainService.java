package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.library.Request;
import ru.practicum.library.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private final MainRepository mainRepository;

    public void hit(Request request) {
        if (request == null) {
            throw new RuntimeException("Request cannot be null");
        } else if (request.getId() != null) {
            throw new RuntimeException("Cannot add request with id");
        }

        mainRepository.save(request);
    }

    public List<Stats> stats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean isUnique) {

        if (start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date");
        }

        if (uris == null || uris.isEmpty()) {
            return isUnique ? mainRepository.getRequestsInPeriodWithUrisGroupedByIpAndUri(start, end) :
                    mainRepository.getRequestsInPeriodWithUrisGroupedByUri(start, end);
        }

        return isUnique ? mainRepository.getRequestsInPeriodWithUrisGroupedByIpAndUri(start, end, uris) :
                mainRepository.getRequestsInPeriodWithUrisGroupedByUri(start, end, uris);
    }
}
