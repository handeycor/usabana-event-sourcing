package com.sabana.event_sourcing_poc.controller;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.domain.view.SalesViewService;
import com.sabana.event_sourcing_poc.entity.SaleViewEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/view/sale")
@AllArgsConstructor
public class SaleViewController {

    private final SalesViewService salesViewService;

    @GetMapping("/all")
    public ResponseEntity<List<SaleViewEntity>> getAllSales() {
        return ResponseEntity.ok(salesViewService.getAllSales());
    }

    @GetMapping("/state")
    public ResponseEntity<List<SaleViewEntity>> getSalesByStatus(@RequestParam(name = "status") final States status) {
        return ResponseEntity.ok(salesViewService.getSalesByStatus(status));
    }

    @GetMapping("/id")
    public ResponseEntity<SaleViewEntity> getSaleById(@RequestParam(name = "id") final Long id) {
        return ResponseEntity.ok(salesViewService.getSaleById(id));
    }

}
