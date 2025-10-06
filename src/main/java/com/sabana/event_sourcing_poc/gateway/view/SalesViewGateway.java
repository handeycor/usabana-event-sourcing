package com.sabana.event_sourcing_poc.gateway.view;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.entity.SaleViewEntity;

import java.util.List;
import java.util.Optional;

public interface SalesViewGateway {

    List<SaleViewEntity> getAllSales();

    Optional<SaleViewEntity> getSaleById(Long id);

    List<SaleViewEntity> getSalesByStatus(States status);
}
