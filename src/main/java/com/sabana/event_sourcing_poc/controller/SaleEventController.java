package com.sabana.event_sourcing_poc.controller;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.domain.events.ProcessingSaleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/event/sale")
@AllArgsConstructor
public class SaleEventController {

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
            log.error("Error paymentSale", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchSale(@RequestParam(name = "sale_id") final Long saleId) {
        try {
            processingSaleService.saveSaleState(saleId, States.DISPATCH);
            return ResponseEntity.ok("successful dispatch");
        } catch (Exception ex) {
            log.error("Error paymentSale", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeSale(@RequestParam(name = "sale_id") final Long saleId) {
        try {
            processingSaleService.saveSaleState(saleId, States.COMPLETE);
            return ResponseEntity.ok("successful complete");
        } catch (Exception ex) {
            log.error("Error paymentSale", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
