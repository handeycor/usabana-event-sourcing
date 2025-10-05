package com.sabana.event_sourcing_poc.domain.gateway;

import com.sabana.event_sourcing_poc.domain.SaleEntity;

public interface ProcessingEvents {

    void createSale(SaleEntity sale);

    void changeStateOfSale(SaleEntity sale);
}
