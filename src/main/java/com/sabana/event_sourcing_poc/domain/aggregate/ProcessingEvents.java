package com.sabana.event_sourcing_poc.domain.aggregate;

import com.sabana.event_sourcing_poc.domain.SaleEntity;

public interface ProcessingEvents {

    void createSale(SaleEntity sale);

    void changeStateOfSale(SaleEntity sale);
}
