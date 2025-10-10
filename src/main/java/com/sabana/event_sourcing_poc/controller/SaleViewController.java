package com.sabana.event_sourcing_poc.controller;

import com.sabana.event_sourcing_poc.domain.view.SalesViewService;
import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.entity.SaleViewEntity;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/view/sale")
@AllArgsConstructor
public class SaleViewController {

    private final SalesViewService salesViewService;

    @GetMapping("/{id}/states")
    public ResponseEntity<List<SaleEventEntity>> getAllSaleStatesById(@PathVariable Long id) {
        return ResponseEntity.ok(salesViewService.getAllSaleStatesById(id));
    }

    @GetMapping("/{id}/states/before")
    public ResponseEntity<List<SaleEventEntity>> getSaleStatesBeforeDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date) {
        return ResponseEntity.ok(salesViewService.getSaleStatesBeforeDate(id, date));
    }

    @GetMapping("/{id}/last")
    public ResponseEntity<SaleEventEntity> getLastSaleState(@PathVariable Long id) {
        return ResponseEntity.ok(salesViewService.getLastSaleState(id));
    }

    @GetMapping("/{id}/last/before")
    public ResponseEntity<SaleViewEntity> getSaleStateAsOfDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date) {
        return ResponseEntity.ok(salesViewService.getSaleStateAsOfDate(id, date));
    }
}
