package com.sabana.event_sourcing_poc.domain.view;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.entity.SaleViewEntity;
import com.sabana.event_sourcing_poc.gateway.view.SalesViewGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalesViewService {

    private final SalesViewGateway salesViewGateway;

    public List<SaleEventEntity> getAllSaleStatesById(final Long saleId) {
        List<SaleEventEntity> saleEntities = salesViewGateway.getAllEventsByIdSale(saleId);
        if (saleEntities.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " does not exist");
        }
        return saleEntities;
    }

    public List<SaleEventEntity> getSaleStatesBeforeDate(final Long saleId, final Instant date) {
        List<SaleEventEntity> saleEntities = salesViewGateway.getAllEventsByIdSaleBeforeDate(saleId, date);
        if (saleEntities.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " not found before date " + date.toString());
        }
        return saleEntities;
    }

    public SaleEventEntity getLastSaleState(final Long saleId) {
        Optional<SaleEventEntity> saleEntity = salesViewGateway.getCurrentSaleState(saleId);
        if (saleEntity.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " does not exist");
        }
        return saleEntity.get();
    }

    /**
     * Reconstructs the sale state by applying all events up to the specified date.
     * Follows the event sourcing pattern by applying each event sequentially.
     *
     * @param saleId   ID of the sale
     * @param asOfDate Date up to which to reconstruct the state
     * @return SaleViewEntity with the sale state as of the specified date
     * @throws IllegalArgumentException if no events are found for the sale or date
     */
    public SaleViewEntity getSaleStateAsOfDate(final Long saleId, final Instant asOfDate) {
        List<SaleEventEntity> events = salesViewGateway.getAllEventsByIdSaleBeforeDate(saleId, asOfDate);

        if (events.isEmpty()) {
            throw new IllegalArgumentException("No events found for sale with id " + saleId + " before date " + asOfDate);
        }

        Instant createdAt = events.getFirst().getLastEventDate();

        States currentStatus = null;
        Instant lastUpdatedAt = createdAt;

        for (SaleEventEntity event : events) {
            currentStatus = event.getStatus();
            lastUpdatedAt = event.getLastEventDate();
        }

        return new SaleViewEntity(
                saleId,
                currentStatus,
                createdAt,
                lastUpdatedAt
        );
    }
}
