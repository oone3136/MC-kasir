package com.category.services;

import com.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.category.repository.CategoryRepository;
import com.category.request.CreateRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;
    public ResponseEntity<String> createCategory(CreateRequest request) {
        try {
            Category category =  new Category();
            category.setNama(request.getNama());
            category.setBanned(false);
            repository.save(category);
            return ResponseEntity.ok("berhasil membuat category");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    public ResponseEntity<String> updateCategory(Integer id, CreateRequest request) {
        try {
            Category findCategory = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("data not found"));
            Integer idCategory = findCategory.getId();
            Category category =  new Category();
            category.setId(idCategory);
            category.setNama(request.getNama());
            repository.save(category);
            return ResponseEntity.ok("berhasil membuat category");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    public ResponseEntity<Map<String, Object>> ListCategory() {
        Map<String, Object> items = new HashMap<>();
        List<Category> categoryList =  repository.findAll();

        List<Category> filteredCategoryList = categoryList.stream()
                .filter(category -> !category.isBanned())
                .collect(Collectors.toList());

        items.put("items", filteredCategoryList);
        items.put("total data", filteredCategoryList.size());

        return ResponseEntity.ok(items);
    }
    public ResponseEntity<Map<String, Object>> getCategoryById(Integer id) {
        Map<String, Object> items = new HashMap<>();
        Category category =  repository.findById(id) .orElseThrow(() -> new RuntimeException("data not found"));
        if (category.isBanned()){
            throw new RuntimeException("id {} terhapus" + id);
        }else {
            items.put("items", category);
        }
            return ResponseEntity.ok(items);
    }
    public ResponseEntity<String> bannedCategory(CreateRequest request, Integer id) {
        Category category =  repository.findById(id) .orElseThrow(() -> new RuntimeException("data not found"));
        Category save =  new Category();
        save.setId(category.getId());
        save.setNama(category.getNama());
        save.setBanned(request.getIsBanned());
        repository.save(save);
        if (request.getIsBanned()){
            return ResponseEntity.ok("id berhasil di hapus " + id);
        }
        return ResponseEntity.ok("berhasil di kembalikan");

    }
}
