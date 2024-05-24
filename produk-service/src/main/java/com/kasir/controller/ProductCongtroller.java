package com.kasir.controller;

import com.kasir.entity.Products;
import com.kasir.request.UpdateStockRequest;
import com.kasir.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductCongtroller {

    private final ProductsService service;

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody Products products) {
        return service.createProduck(products);
    }
    @PostMapping("product/{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a file");
        }
        try {
            return service.updateImageProducts(id, file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer id) {
        byte[] imageData = service.getProductImage(id);
        if (imageData != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Integer id, @RequestBody Products products) {
        return service.updateProduck(id, products);
    }
    @PutMapping("/product/stock/{id}")
    public ResponseEntity<String> updateStock(@PathVariable("id") Integer id, @RequestBody UpdateStockRequest request) {
        return service.updateStock(request, id);
    }
    @GetMapping("/product")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        return service.getAllProducts();
    }
    @GetMapping("/product/{id}")
    public Products getProductsById(@PathVariable("id") Integer id) {
        return service.getProductById(id);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduk(@PathVariable("id") Integer id) {
        return service.deleteProduk(id);
    }
}
