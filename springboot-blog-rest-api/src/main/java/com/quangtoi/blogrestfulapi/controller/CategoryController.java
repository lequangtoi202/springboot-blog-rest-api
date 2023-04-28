package com.quangtoi.blogrestfulapi.controller;

import com.quangtoi.blogrestfulapi.dto.CategoryDto;
import com.quangtoi.blogrestfulapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;


    @Operation(
            summary = "Create category REST API"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all category REST API"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get category REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id){
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update category REST API"
    )
    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") long id, @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete category REST API"
    )
    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Delete Category Successfully", HttpStatus.OK);
    }
}
