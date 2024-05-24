package com.accounting.pembukuan.controller;

import com.accounting.pembukuan.entity.LB;
import com.accounting.pembukuan.request.DateRange;
import com.accounting.pembukuan.request.LBRequest;
import com.accounting.pembukuan.services.bulanan.LBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LBController {

    private final LBService service;

    @PostMapping("/lh1")
    public ResponseEntity<String> createLaporanHarian(@RequestBody LBRequest request) {
        return service.createLaporanHarian(request);
    }
    @PostMapping("/lh1/debit")
    public ResponseEntity<Map<String, Object>>aruskasDebit(@RequestBody DateRange request) {

        return service.aruskasDebit(request.getStartDate(), request.getEndDate());
    }
    @PostMapping("/lh1/kredit")
    public ResponseEntity<Map<String, Object>>aruskasKredit(@RequestBody DateRange request) {

        return service.aruskasKredit(request.getStartDate(), request.getEndDate());
    }
}
