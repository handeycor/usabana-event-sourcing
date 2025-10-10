package com.sabana.event_sourcing_poc.domain.events;


import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.domain.view.SalesViewService;
import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.gateway.event.ProcessingEvents;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessingSaleService {

    private final ProcessingEvents processingEvents;
    private final SalesViewService salesViewService;

    public Long createSale() {
        final Long saleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        final SaleEventEntity saleEventEntity = new SaleEventEntity(saleId, States.CREATED);
        processingEvents.createSale(saleEventEntity);
        return saleId;
    }

    public void saveSaleState(final Long saleId, final States status) {
        final SaleEventEntity lastStateOfSale = salesViewService.getLastSaleState(saleId);


        final String nextStateOfCurrent = lastStateOfSale.getStatus().getNextState();
        if (nextStateOfCurrent == null) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " has complete");
        }

        if (!nextStateOfCurrent.equals(status.name())) {
            throw new IllegalArgumentException("id: " + saleId + " - rejected invalid state event");
        }

        final SaleEventEntity saleEventEntity = new SaleEventEntity(saleId, status);

        processingEvents.changeStateOfSale(saleEventEntity);
    }

}
