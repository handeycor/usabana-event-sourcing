package com.sabana.event_sourcing_poc.domain.gateway;

import com.sabana.event_sourcing_poc.domain.SaleEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ReadSaleEvents {
    List<SaleEntity> getSaleStatesBeforeDate(Long saleId, Instant date);

    Optional<SaleEntity> getLastStateOfSale(Long saleId);

}
