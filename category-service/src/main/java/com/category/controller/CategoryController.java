package com.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.category.request.CreateRequest;
import com.category.services.CategoryService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService service;

    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody CreateRequest request) {
        return service.createCategory(request);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable("id") Integer id) {
        return service.getCategoryById(id);
    }

    @PostMapping("/category/banned/{id}")
    public ResponseEntity<String> bannedCategory(@RequestBody CreateRequest request, @PathVariable("id") Integer id) {
        return service.bannedCategory(request, id);
    }
    @GetMapping("/category")
    public ResponseEntity<Map<String, Object>> ListCategory() {
        return service.ListCategory();
    }
}
