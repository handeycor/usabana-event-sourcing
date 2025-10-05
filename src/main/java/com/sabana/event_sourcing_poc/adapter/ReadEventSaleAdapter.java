package com.sabana.event_sourcing_poc.adapter;

import com.sabana.event_sourcing_poc.domain.SaleEntity;
import com.sabana.event_sourcing_poc.domain.gateway.ReadSaleEvents;
import com.sabana.event_sourcing_poc.repository.ReadEventSale;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ReadEventSaleAdapter implements ReadSaleEvents {

    private final ReadEventSale readEventSale;

    @Override
    public List<SaleEntity> getSaleStatesBeforeDate(final Long saleId, final Instant date) {
        return readEventSale.findAllBySaleIdAndLastEventDateBefore(saleId, date)
                .stream()
                .map(event ->
                        new SaleEntity(
                                event.getSaleId(),
                                event.getStatus(),
                                event.getLastEventDate()
                        )
                ).toList();
    }

    @Override
    public Optional<SaleEntity> getLastStateOfSale(final Long saleId) {

        return Optional.ofNullable(readEventSale.findFirstBySaleIdOrderByLastEventDateDesc(saleId))
                .map(event -> new SaleEntity(
                        event.getSaleId(),
                        event.getStatus(),
                        event.getLastEventDate()
                ));
    }
}
