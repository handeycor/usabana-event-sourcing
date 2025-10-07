package com.sabana.event_sourcing_poc.adapter.view;

import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.gateway.view.SalesViewGateway;
import com.sabana.event_sourcing_poc.repository.view.SalesViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SalesViewAdapter implements SalesViewGateway {

    private final SalesViewRepository salesViewRepository;


    @Override
    public List<SaleEventEntity> getAllEventsByIdSale(Long idSale) {
        return salesViewRepository.findAllBySaleIdOrderByLastEventDateAsc(idSale).stream()
                .map(saleView -> new SaleEventEntity(
                        saleView.getSaleId(),
                        saleView.getStatus(),
                        saleView.getLastEventDate()
                ))
                .toList();
    }

    @Override
    public List<SaleEventEntity> getAllEventsByIdSaleBeforeDate(Long idSale, Instant date) {

        return salesViewRepository.findAllBySaleIdAndLastEventDateBeforeOrderByLastEventDateAsc(idSale, date).stream()
                .map(saleView -> new SaleEventEntity(
                        saleView.getSaleId(),
                        saleView.getStatus(),
                        saleView.getLastEventDate()
                ))
                .toList();
    }

    @Override
    public Optional<SaleEventEntity> getCurrentSaleState(Long idSale) {
        return Optional.ofNullable(salesViewRepository.findFirstBySaleIdOrderByLastEventDateDesc(idSale))
                .map(saleView -> new SaleEventEntity(
                        saleView.getSaleId(),
                        saleView.getStatus(),
                        saleView.getLastEventDate()
                ));
    }
}
