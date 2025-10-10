package com.sabana.event_sourcing_poc.gateway;

import com.sabana.event_sourcing_poc.repository.event.model.SaleEvent;

public interface PublishSaleEvent {

    void publish(SaleEvent saleEvent);
}
