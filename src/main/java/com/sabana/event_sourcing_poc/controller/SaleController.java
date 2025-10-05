package com.sabana.event_sourcing_poc.controller;

import com.sabana.event_sourcing_poc.domain.ProcessingSaleService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class SaleController {

    private final ProcessingSaleService processingSaleService;


    @PostMapping("/sale")
    public ResponseEntity<Long> createSale() {
        return ResponseEntity.ok(processingSaleService.createSale());
    }

}
