package com.kasir.services;

import com.kasir.request.Category;
import com.kasir.entity.Products;
import com.kasir.repository.ProductsRepository;
import com.kasir.request.UpdateStockRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository repository;
    private final WebClient webClient;
    private final SendDataImpl sendData;

    private static final Logger logger = LoggerFactory.getLogger(Logger.class);

    public ResponseEntity<String> createProduck(Products products) {
        try {
            Category category = webClient.get()
                    .uri("/category/{id}", products.getCategoriName())
                    .retrieve()
                    .bodyToMono(Category.class)
                    .block();
            if (category == null) {
                throw new RuntimeException("Category notfound");
            }
            if (products.getName().isEmpty()) {
                throw new RuntimeException("name tidak boleh kosong");
            }
            if (products.getPrice() == 0) {
                throw new RuntimeException("price harus di isi");
            }
            if (products.getStock() == 0) {
                throw new RuntimeException("stok tidak boleh kosong");
            }
            Products products2 = Products.builder()
                    .name(products.getName())
                    .stock(products.getStock())
                    .price(products.getPrice())
                    .hargaJual(products.getHargaJual())
                    .categoriName(products.getCategoriName())
                    .build();
            sendData.sendReport(products2);
            repository.save(products2);

            logger.info("laporan",products2);

            return ResponseEntity.ok("produk berhasil di tambahkan");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    public ResponseEntity<?> updateImageProducts(Integer id, MultipartFile imageProducts) {
        try {
            Products products = repository.findById(id).orElse(null);
            if (products == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
            }
            byte[] imageByte = IOUtils.toByteArray(imageProducts.getInputStream());
            products.setImageProducts(imageByte);
            repository.save(products);
            return ResponseEntity.ok("produk image update succesfull");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update product");
        }
    }
    public byte[] getProductImage(Integer id) {
        Optional<Products> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            return product.getImageProducts();
        }
        return null;
    }

    public ResponseEntity<String> updateProduck(Integer id, Products products) {
        try {
            if (products.getName().isEmpty()) {
                throw new RuntimeException("name tidak boleh kosong");
            }
            if (products.getPrice() == 0) {
                throw new RuntimeException("price harus di isi");
            }
            if (products.getStock() == 0) {
                throw new RuntimeException("stok tidak boleh kosong");
            }

            Products products2 = Products.builder()
                    .name(products.getName())
                    .stock(products.getStock())
                    .price(products.getPrice())
                    .hargaJual(products.getHargaJual())
                    .build();
            repository.save(products2);
            return ResponseEntity.ok("produk berhasil di update");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        try {
            Map<String, Object> items = new HashMap<>();
            List<Products> products = repository.findAll();
            if (products.isEmpty()) {
                throw new RuntimeException("data not found");
            }
            items.put("items", products);
            items.put("total data", products.size());
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    public Products getProductById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
    }
    public ResponseEntity<String> deleteProduk(Integer id) {
        try {
            Optional<Products> products = repository.findById(id);
            if (products.isEmpty()){
                throw new RuntimeException("produk tidak ada");
            }
            repository.deleteById(id);
            return ResponseEntity.ok("produk berhasil dihapus");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    public ResponseEntity<String>updateStock(UpdateStockRequest request, Integer id) {
        Products products = repository.findById(id).orElseThrow(() -> new RuntimeException("data not found"));
        Products update = new Products();
        update.setId(products.getId());
        update.setName(products.getName());
        update.setStock(request.getStock());
        update.setPrice(products.getPrice());
        update.setHargaJual(products.getHargaJual());
        update.setCategoriName(products.getCategoriName());
        update.setImageProducts(getProductImage(products.getId()));
        repository.save(update);
        return ResponseEntity.ok("berhasil mengupdate stok");
    }

}
