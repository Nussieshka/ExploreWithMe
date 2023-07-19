package ru.practicum.main_service.service;

import ru.practicum.main_service.model.dto.CompilationDTO;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;

import java.util.List;

public interface CompilationService {
    CompilationDTO addCompilation(CreatedCompilationDTO compilation);

    void deleteCompilation(Long compId);

    CompilationDTO editCompilation(Long compId, CreatedCompilationDTO compilation);

    List<CompilationDTO> getCompilations(Boolean pinned, Integer from, Integer size, String clientIp, String endpointPath);

    CompilationDTO getCompilation(Long compId, String clientIp, String endpointPath);
}
