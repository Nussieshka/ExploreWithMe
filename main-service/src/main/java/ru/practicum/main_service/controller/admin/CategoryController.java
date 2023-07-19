package ru.practicum.main_service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.CategoryDTO;
import ru.practicum.main_service.service.CategoryService;

import javax.validation.Valid;

@RestController("adminCategoryController")
@RequestMapping("/admin/categories")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCategory(category));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long catId) {
        service.deleteCategory(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDTO> patchCategory(@PathVariable Long catId,
                                                     @RequestBody @Valid CategoryDTO category) {
        return ResponseEntity.ok(service.patchCategory(catId, category));
    }
}
