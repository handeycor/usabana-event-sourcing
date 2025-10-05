package com.sabana.event_sourcing_poc.repository.adapter;

import com.sabana.event_sourcing_poc.domain.SaleEntity;
import com.sabana.event_sourcing_poc.domain.aggregate.ProcessingEvents;
import com.sabana.event_sourcing_poc.repository.model.SaleEvent;
import com.sabana.event_sourcing_poc.repository.write.SaveEventSale;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class ProcessingEventsSaleAdapter implements ProcessingEvents {

    private final SaveEventSale saveEventSale;


    @Override
    public void createSale(final SaleEntity saleEntity) {
        SaleEvent saleEvent = new SaleEvent();
        saleEvent.setSaleId(saleEntity.getSaleId());
        saleEvent.setStatus(saleEntity.getStatus());
        saleEvent.setLastEventDate(Instant.now());
        saveEventSale.save(saleEvent);
    }


    @Override
    public void changeStateOfSale(SaleEntity saleEntity) {
        SaleEvent saleEvent = new SaleEvent();
        saleEvent.setSaleId(saleEntity.getSaleId());
        saleEvent.setStatus(saleEntity.getStatus());
        saleEvent.setLastEventDate(Instant.now());
        saveEventSale.save(saleEvent);
    }

}
