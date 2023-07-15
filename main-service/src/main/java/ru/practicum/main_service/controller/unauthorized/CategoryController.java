package ru.practicum.main_service.controller.unauthorized;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CategoryDTO;

import java.util.List;

@RestController("publicCategoryController")
@RequestMapping("/categories")
public class CategoryController {
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String catId) {
        return null;
    }
}
