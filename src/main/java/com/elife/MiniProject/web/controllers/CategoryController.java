package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.CategoryService;
import com.elife.MiniProject.dao.entities.Category;
import com.elife.MiniProject.web.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COLLABORATOR') and hasAuthority ('READ_PRIVILEGE')")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(CategoryDTO.convertToDTO(category));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COLLABORATOR') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories().stream()
                .map(CategoryDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('WRITE_PRIVILEGE')")

    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryDTO.convertToEntity();
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(CategoryDTO.convertToDTO(savedCategory));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')and hasAuthority('UPDATE_PRIVILEGE')")

    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryDTO.convertToEntity();
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return ResponseEntity.ok(CategoryDTO.convertToDTO(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')and hasAuthority('DELETE_PRIVILEGE')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

