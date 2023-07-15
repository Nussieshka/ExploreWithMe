package ru.practicum.main_service.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CategoryDTO;

@RestController("adminCategoryController")
@RequestMapping("/admin/categories")
public class CategoryController {

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO category) {
        return null;
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer catId) {
        return null;
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDTO> patchCategory(@PathVariable String catId, @RequestBody CategoryDTO category) {
        return null;
    }
}
