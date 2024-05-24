package com.pembayaran.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pembayaran.request.PMCreateRequest;
import com.pembayaran.service.PMCratedService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PMController {

    private final PMCratedService createdService;

    @PostMapping("/PM")
    public ResponseEntity<String> createPM(@RequestBody PMCreateRequest request) {
        return createdService.createPembayaran(request);
    }
}
