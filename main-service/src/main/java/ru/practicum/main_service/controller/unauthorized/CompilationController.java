package ru.practicum.main_service.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CompilationDTO;
import ru.practicum.main_service.service.CompilationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("publicCompilationController")
@RequestMapping("/compilations")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompilationController {
    private final CompilationService service;

    @GetMapping
    public ResponseEntity<List<CompilationDTO>> getCompilations(@RequestParam(defaultValue = "False") Boolean pinned,
                                                                @RequestParam(defaultValue = "0") Integer from,
                                                                @RequestParam(defaultValue = "10") Integer size,
                                                                HttpServletRequest request) {
        return ResponseEntity.ok(service.getCompilations(pinned, from, size,
                request.getRemoteAddr(), request.getRequestURI()));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDTO> getCompilation(@PathVariable Long compId, HttpServletRequest request) {
        return ResponseEntity.ok(service.getCompilation(compId, request.getRemoteAddr(), request.getRequestURI()));
    }
}
