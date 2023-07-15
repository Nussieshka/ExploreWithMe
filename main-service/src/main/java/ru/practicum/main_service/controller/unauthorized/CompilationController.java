package ru.practicum.main_service.controller.unauthorized;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CompilationDTO;

import java.util.List;

@RestController("publicCompilationController")
@RequestMapping("/compilations")
public class CompilationController {
    @GetMapping
    public ResponseEntity<List<CompilationDTO>> getCompilations(@RequestParam(defaultValue = "False") Boolean pinned,
                                                          @RequestParam(defaultValue = "0") Integer from,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDTO> getCompilation(@PathVariable Long compId) {
        return null;
    }
}
