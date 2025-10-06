package com.sabana.event_sourcing_poc.gateway.event;

import com.sabana.event_sourcing_poc.entity.SaleEventEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ReadSaleEvents {
    List<SaleEventEntity> getSaleStatesBeforeDate(Long saleId, Instant date);

    Optional<SaleEventEntity> getLastStateOfSale(Long saleId);

}
