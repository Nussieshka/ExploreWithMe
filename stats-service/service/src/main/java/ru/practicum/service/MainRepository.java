package ru.practicum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.library.Request;
import ru.practicum.library.Stats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MainRepository extends JpaRepository<Request, Long> {
    @Query("SELECT new ru.practicum.library.Stats(request.app, request.uri, COUNT(DISTINCT request.ip)) " +
            "FROM Request request WHERE request.timestamp >= :start " +
            "AND request.timestamp <= :end AND request.uri IN :uris GROUP BY request.uri, request.app " +
            "ORDER BY COUNT(DISTINCT request.ip) DESC")
    List<Stats> getRequestsInPeriodWithUrisGroupedByIpAndUri(
            LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("SELECT new ru.practicum.library.Stats(request.app, request.uri, COUNT(DISTINCT request.ip)) " +
            "FROM Request request WHERE request.timestamp >= :start " +
            "AND request.timestamp <= :end GROUP BY request.uri, request.app " +
            "ORDER BY COUNT(DISTINCT request.ip) DESC")
    List<Stats> getRequestsInPeriodWithUrisGroupedByIpAndUri(
            LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.library.Stats(request.app, request.uri, COUNT(request.uri)) " +
            "FROM Request request WHERE request.timestamp >= :start " +
            "AND request.timestamp <= :end AND request.uri IN :uris GROUP BY request.uri, request.app " +
            "ORDER BY COUNT(request.uri) DESC")
    List<Stats> getRequestsInPeriodWithUrisGroupedByUri(
            LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("SELECT new ru.practicum.library.Stats(request.app, request.uri, COUNT(request.uri)) " +
            "FROM Request request WHERE request.timestamp >= :start " +
            "AND request.timestamp <= :end GROUP BY request.uri, request.app " +
            "ORDER BY COUNT(request.uri) DESC")
    List<Stats> getRequestsInPeriodWithUrisGroupedByUri(
            LocalDateTime start, LocalDateTime end);
}
