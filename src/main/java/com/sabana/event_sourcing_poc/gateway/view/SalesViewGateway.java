package com.sabana.event_sourcing_poc.gateway.view;

import com.sabana.event_sourcing_poc.entity.SaleEventEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SalesViewGateway {

    List<SaleEventEntity> getAllEventsByIdSale(Long idSale);

    List<SaleEventEntity> getAllEventsByIdSaleBeforeDate(Long idSale, Instant date);

    Optional<SaleEventEntity> getCurrentSaleState(Long idSale);
}
