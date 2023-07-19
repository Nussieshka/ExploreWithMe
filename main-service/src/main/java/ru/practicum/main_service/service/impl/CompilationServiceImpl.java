package ru.practicum.main_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.main_service.ExploreWithMe;
import ru.practicum.main_service.model.dto.CompilationDTO;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;
import ru.practicum.main_service.model.entity.CompilationEntity;
import ru.practicum.main_service.model.entity.EventEntity;
import ru.practicum.main_service.model.mapper.CompilationMapper;
import ru.practicum.main_service.model.mapper.CompilationUpdateMapper;
import ru.practicum.main_service.repository.EventRepository;
import ru.practicum.main_service.service.CompilationService;
import ru.practicum.main_service.repository.CompilationRepository;
import ru.practicum.main_service.util.MessageResponseStatusException;
import ru.practicum.main_service.util.Util;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    private final StatsClient statsClient;

    @Override
    public CompilationDTO addCompilation(CreatedCompilationDTO compilation) {
        List<EventEntity> events = toEventList(compilation.getEvents());

        return CompilationMapper.INSTANCE.toCompilationDTO(
                compilationRepository.save(CompilationMapper.INSTANCE.toCompilationEntity(compilation, events)));
    }

    @Override
    public void deleteCompilation(Long compId) {
        if (compId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDTO editCompilation(Long compId, CreatedCompilationDTO compilationDTO) {
        if (compId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }
        Util.validateCompilationDTO(compilationDTO);
        CompilationEntity compilation = compilationRepository.findById(compId).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Compilation", compId));

        List<Long> eventIds = compilationDTO.getEvents();
        List<EventEntity> events;
        if (eventIds == null) {
            events = null;
        } else {
            events = eventRepository.findAllByIdIn(eventIds);
        }

        CompilationUpdateMapper.INSTANCE.updateCompilationEntityFromUpdateCompilationDTO(
                compilationDTO, compilation, events);

        return CompilationMapper.INSTANCE.toCompilationDTO(compilationRepository.save(compilation));
    }

    @Override
    public List<CompilationDTO> getCompilations(Boolean pinned, Integer from, Integer size, String clientIp,
                                                String endpointPath) {
        if (pinned == null) {
            return CompilationMapper.INSTANCE.toCompilationDTO(
                    compilationRepository.findAll(PageRequest.of(from / size, size)));
        }

        List<CompilationDTO> compilationDTOS = CompilationMapper.INSTANCE.toCompilationDTO(compilationRepository.findAllByPinned(pinned,
                PageRequest.of(from / size, size)));

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        return compilationDTOS;
    }

    @Override
    public CompilationDTO getCompilation(Long compId, String clientIp, String endpointPath) {
        if (compId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        CompilationDTO compilationDTO = compilationRepository.findById(compId)
                .map(CompilationMapper.INSTANCE::toCompilationDTO).orElseThrow(
                () -> MessageResponseStatusException.getNotFoundException("Compilation", compId));

        statsClient.hit(ExploreWithMe.APP_NAME, endpointPath, clientIp, LocalDateTime.now());

        return compilationDTO;
    }

    private List<EventEntity> toEventList(List<Long> eventIds) {
        List<EventEntity> events;
        if (eventIds == null || eventIds.isEmpty()) {
            events = Collections.emptyList();
        } else {
            events = eventRepository.findAllByIdIn(eventIds);
        }
        return events;
    }
}
