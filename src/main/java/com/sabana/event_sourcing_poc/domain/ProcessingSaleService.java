package com.sabana.event_sourcing_poc.domain;


import com.sabana.event_sourcing_poc.domain.aggregate.ProcessingEvents;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessingSaleService {

    private final ProcessingEvents processingEvents;

    public Long createSale() {
        final Long saleId =  Long.valueOf(UUID.randomUUID().toString());
        final SaleEntity saleEntity = new SaleEntity(saleId, "CREATED");
        processingEvents.createSale(saleEntity);
        return saleId;
    }

    public void paymentSale(final Long saleId) {
        final SaleEntity saleEntity = new SaleEntity(saleId, "PAYMENT");
        processingEvents.changeStateOfSale(saleEntity);
    }

    public void sendSale(final Long saleId) {
        final SaleEntity saleEntity = new SaleEntity(saleId, "DISPATCH");
        processingEvents.changeStateOfSale(saleEntity);
    }

    public void completeSale(final Long saleId) {
        final SaleEntity saleEntity = new SaleEntity(saleId, "COMPLETE");
        processingEvents.changeStateOfSale(saleEntity);
    }

}
