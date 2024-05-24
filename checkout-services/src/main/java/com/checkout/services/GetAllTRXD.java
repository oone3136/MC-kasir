package com.checkout.services;

import com.checkout.entity.TRXDetails;
import com.checkout.repository.TRXDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTRXD {

    private final TRXDRepository repository;

    public List<TRXDetails>getAllTRXD(){
        return repository.findAll();
    }
}
