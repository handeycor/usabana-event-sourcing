package com.sabana.event_sourcing_poc.adapter.view;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.entity.SaleViewEntity;
import com.sabana.event_sourcing_poc.gateway.view.SalesViewGateway;
import com.sabana.event_sourcing_poc.repository.view.SalesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SalesViewAdapter implements SalesViewGateway {

    private final SalesRepository salesRepository;

    @Override
    public List<SaleViewEntity> getAllSales() {

        return salesRepository.findAll()
                .stream()
                .map(sale -> new SaleViewEntity(sale.getSaleId(), sale.getStatus(), sale.getCreatedAt(), sale.getLastUpdatedAt()))
                .toList();
    }

    @Override
    public Optional<SaleViewEntity> getSaleById(Long id) {
        return salesRepository.findById(id)
                .map(sale -> new SaleViewEntity(sale.getSaleId(), sale.getStatus(), sale.getCreatedAt(), sale.getLastUpdatedAt()));
    }

    @Override
    public List<SaleViewEntity> getSalesByStatus(States status) {

        return salesRepository.findByStatus(status)
                .stream()
                .map(sale -> new SaleViewEntity(sale.getSaleId(), sale.getStatus(), sale.getCreatedAt(), sale.getLastUpdatedAt()))
                .toList();
    }

}
