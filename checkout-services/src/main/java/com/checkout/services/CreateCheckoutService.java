package com.checkout.services;

import com.checkout.entity.Checkout;
import com.checkout.repository.CheckoutRepository;
import com.checkout.request.CheckRequest;
import com.checkout.request.ProductRequest;
import com.checkout.request.UpdateStokProductRequest;
import com.checkout.status.StatusCheckout;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreateCheckoutService {

    private final CheckoutRepository repository;
    private final WebClient webClient;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy")
    private LocalDate idDate;
    private int idInt = 4;

    public String generateId() {
        Random random = new Random();
        idInt = random.nextInt();
        return "TRX" + idInt;
    }

    public ResponseEntity<String> createCheckout(CheckRequest request) {
        cekInput(request);
        crete(request);
        return ResponseEntity.ok("checkout sucses");
    }
    public void cekInput(CheckRequest request) {
        if (request.getProducts().isEmpty() ) {
            throw new RuntimeException("product tidak boleh kosong");
        }

    }
    public void crete(CheckRequest request) {

        ProductRequest productRequest = webClient.get()
                .uri("/product/{id}", request.getProducts())
                .retrieve()
                .bodyToMono(ProductRequest.class)
                .block();
        if (productRequest == null) {
            throw new RuntimeException("get product gagal atau product tidak ada");
        }
        if (productRequest.getStock() < request.getQuantity()) {
            throw new RuntimeException("product tidak mencukupi");
        }

        Integer qty = productRequest.getStock() - request.getQuantity();
        Long subTot = productRequest.getHargaJual() * request.getQuantity();

        UpdateStokProductRequest stokRequest = new UpdateStokProductRequest();
        stokRequest.setStock(qty);

        webClient.put()
            .uri("/product/stock/{id}", request.getProducts())
            .bodyValue(stokRequest)
            .retrieve()
            .bodyToMono(Void.class)
            .block();

        Optional<Checkout> checkout = repository.findByProducts(request.getProducts());
        if (checkout.isPresent()) {
            if (checkout.get().getStatus() == StatusCheckout.BELUMDICHEKOUT) {
                Checkout data = checkout.get();
                Integer totalQty = request.getQuantity() + data.getQuantity();
                Long Sub = totalQty * productRequest.getHargaJual();

                data.setQuantity(totalQty);
                data.setSubTotal(Sub);
                repository.save(data);
            }
        }else{
            Checkout save = new Checkout();
            save.setId(generateId());
            save.setProducts(request.getProducts());
            save.setQuantity(request.getQuantity());
            save.setSubTotal(subTot);
            save.setCreatedAt(LocalDate.now());
            save.setCreatedTime(LocalTime.now());
            save.setStatus(StatusCheckout.BELUMDICHEKOUT);
            repository.save(save);
        }
    }
}
