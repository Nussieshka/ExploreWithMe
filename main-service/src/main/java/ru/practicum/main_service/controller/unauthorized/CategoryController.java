package ru.practicum.main_service.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CategoryDTO;
import ru.practicum.main_service.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("publicCategoryController")
@RequestMapping("/categories")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size,
                                                           HttpServletRequest request) {
        return ResponseEntity.ok(service.getCategories(from, size, request.getRemoteAddr(), request.getRequestURI()));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long catId, HttpServletRequest request) {
        return ResponseEntity.ok(service.getCategory(catId, request.getRemoteAddr(), request.getRequestURI()));
    }
}
