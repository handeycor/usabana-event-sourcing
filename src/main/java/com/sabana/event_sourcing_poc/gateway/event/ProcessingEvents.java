package com.sabana.event_sourcing_poc.gateway.event;

import com.sabana.event_sourcing_poc.entity.SaleEventEntity;

public interface ProcessingEvents {

    void createSale(SaleEventEntity sale);

    void changeStateOfSale(SaleEventEntity sale);
}
