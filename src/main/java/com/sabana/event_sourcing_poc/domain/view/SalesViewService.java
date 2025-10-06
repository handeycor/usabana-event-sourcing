package com.sabana.event_sourcing_poc.domain.view;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.entity.SaleViewEntity;
import com.sabana.event_sourcing_poc.gateway.view.SalesViewGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalesViewService {

    private final SalesViewGateway salesViewGateway;

    public List<SaleViewEntity> getSalesByStatus(States status) {
        return salesViewGateway.getSalesByStatus(status);
    }

    public List<SaleViewEntity> getAllSales() {
        return salesViewGateway.getAllSales();
    }

    public SaleViewEntity getSaleById(Long id) {
        final Optional<SaleViewEntity> sales = salesViewGateway.getSaleById(id);

        if (sales.isEmpty()) {
            throw new IllegalArgumentException("SaleViewEntity with id " + id + " does not exist");
        }

        return sales.get();
    }
}
