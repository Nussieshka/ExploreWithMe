package ru.practicum.main_service.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CompilationDTO;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;

@RestController("adminCompilationController")
@RequestMapping("/admin/compilations")
public class CompilationController {
    @PostMapping
    public ResponseEntity<CompilationDTO> addCompilation(@RequestBody CreatedCompilationDTO compilation) {
        return null;
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable String compId) {
        return null;
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDTO> editCompilation(@PathVariable String compId,
                                                  @RequestBody CreatedCompilationDTO compilation) {
        return null;
    }
}
