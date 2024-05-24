package com.checkout.controller;

import com.checkout.entity.TRXDetails;
import com.checkout.request.TRXDByNameRequest;
import com.checkout.request.TRXDEtailsRequest;
import com.checkout.response.TrxDetailsResponse;
import com.checkout.services.GetAllTRXD;
import com.checkout.services.GetTrxDetailsServices;
import com.checkout.services.TRXDDeleteteService;
import com.checkout.services.TRXDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TRXDController {
    private final TRXDService service;
    private final GetTrxDetailsServices trxSercice;
    private final GetAllTRXD getAllTRXD;
    private final TRXDDeleteteService trxDelete;

    @PostMapping("/trxD")
    public ResponseEntity<String> addTrxDetails(@RequestBody TRXDEtailsRequest request) {
        return service.createTRXD(request);
    }
    @PostMapping("/trxD/getByPemesan")
    public TrxDetailsResponse getTrxDetails(@RequestBody TRXDByNameRequest request){
        return trxSercice.getTrxDetails(request);
    }
    @PutMapping("/trxD/us/{id}")
    public ResponseEntity<String> updateStatusTRXD(@PathVariable("id")Integer id) {
        return trxDelete.updateStatusTRXD(id);
    }
    @GetMapping("/trxD/get")
    public List<TRXDetails>getAllTrxD(){
        return getAllTRXD.getAllTRXD();
    }
}
