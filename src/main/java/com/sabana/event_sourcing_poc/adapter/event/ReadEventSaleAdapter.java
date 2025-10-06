package com.sabana.event_sourcing_poc.adapter.event;

import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.gateway.event.ReadSaleEvents;
import com.sabana.event_sourcing_poc.repository.event.ReadEventSale;
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
    public List<SaleEventEntity> getSaleStatesBeforeDate(final Long saleId, final Instant date) {
        return readEventSale.findAllBySaleIdAndLastEventDateBefore(saleId, date)
                .stream()
                .map(event ->
                        new SaleEventEntity(
                                event.getSaleId(),
                                event.getStatus(),
                                event.getLastEventDate()
                        )
                ).toList();
    }

    @Override
    public Optional<SaleEventEntity> getLastStateOfSale(final Long saleId) {

        return Optional.ofNullable(readEventSale.findFirstBySaleIdOrderByLastEventDateDesc(saleId))
                .map(event -> new SaleEventEntity(
                        event.getSaleId(),
                        event.getStatus(),
                        event.getLastEventDate()
                ));
    }
}
