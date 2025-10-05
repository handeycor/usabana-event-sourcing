package com.sabana.event_sourcing_poc.controller;

import com.sabana.event_sourcing_poc.domain.ProcessingSaleService;
import com.sabana.event_sourcing_poc.domain.SaleEntity;
import com.sabana.event_sourcing_poc.domain.States;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/event/sale")
@AllArgsConstructor
public class SaleController {

    private final ProcessingSaleService processingSaleService;


    @PostMapping("/create")
    public ResponseEntity<Long> createSale() {
        return ResponseEntity.ok(processingSaleService.createSale());
    }

    @PostMapping("/payment")
    public ResponseEntity<String> paymentSale(@RequestParam(name = "sale_id") final Long saleId) {
        try {
            processingSaleService.saveSaleState(saleId, States.PAYMENT);
            return ResponseEntity.ok("successful payment");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchSale(@RequestParam(name = "sale_id") final Long saleId) {
        try {
            processingSaleService.saveSaleState(saleId, States.DISPATCH);
            return ResponseEntity.ok("successful dispatch");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeSale(@RequestParam(name = "sale_id") final Long saleId) {
        try {
            processingSaleService.saveSaleState(saleId, States.COMPLETE);
            return ResponseEntity.ok("successful complete");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<SaleEntity> getSaleState(@RequestParam(name = "sale_id") final Long saleId) {
        try {
            return ResponseEntity.ok(processingSaleService.getLastSaleState(saleId));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<SaleEntity>> getSaleHistory(@RequestParam(name = "sale_id") final Long saleId,
                                                         @RequestParam(name = "date") final Instant date) {
        try {
            return ResponseEntity.ok(processingSaleService.getSaleStatesBeforeDate(saleId, date));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
}
