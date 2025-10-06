package com.sabana.event_sourcing_poc.adapter.event;

import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.gateway.PublishSaleEvent;
import com.sabana.event_sourcing_poc.gateway.event.ProcessingEvents;
import com.sabana.event_sourcing_poc.repository.event.SaveEventSale;
import com.sabana.event_sourcing_poc.repository.event.model.SaleEvent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class ProcessingEventsSaleAdapter implements ProcessingEvents {

    private final SaveEventSale saveEventSale;
    private final PublishSaleEvent publishSaleEvent;

    @Transactional
    @Override
    public void createSale(final SaleEventEntity saleEventEntity) {
        SaleEvent saleEvent = new SaleEvent();
        saleEvent.setSaleId(saleEventEntity.getSaleId());
        saleEvent.setStatus(saleEventEntity.getStatus());
        saleEvent.setLastEventDate(Instant.now());
        saveEventSale.save(saleEvent);

        publishSaleEvent.publish(saleEvent);
    }

    @Transactional
    @Override
    public void changeStateOfSale(SaleEventEntity saleEventEntity) {
        SaleEvent saleEvent = new SaleEvent();
        saleEvent.setSaleId(saleEventEntity.getSaleId());
        saleEvent.setStatus(saleEventEntity.getStatus());
        saleEvent.setLastEventDate(Instant.now());
        saveEventSale.save(saleEvent);

        publishSaleEvent.publish(saleEvent);
    }


}
