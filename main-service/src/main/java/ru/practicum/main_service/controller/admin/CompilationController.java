package ru.practicum.main_service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CompilationDTO;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;
import ru.practicum.main_service.service.CompilationService;

import javax.validation.Valid;

@RestController("adminCompilationController")
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class CompilationController {

    private final CompilationService service;

    @PostMapping
    public ResponseEntity<CompilationDTO> addCompilation(@RequestBody @Valid CreatedCompilationDTO compilation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCompilation(compilation));
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable Long compId) {
        service.deleteCompilation(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDTO> editCompilation(@PathVariable Long compId,
                                                          @RequestBody CreatedCompilationDTO compilation) {
        return ResponseEntity.ok(service.editCompilation(compId, compilation));
    }
}
